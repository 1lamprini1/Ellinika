package com.example.lamprini.ellinika.db;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Lamprini Georgatsou
 */

public class DatabaseAccessor {

    private final AppDatabase db;

    public DatabaseAccessor(Context context) { db = AppDatabase.getInstance(context); }

    public User createUser(String name, String pass) throws ExecutionException, InterruptedException {

        CreateUserTask task = new CreateUserTask(db);
        return task.execute(name, pass).get();

    }

    public User getUser(String name) throws ExecutionException, InterruptedException {

        GetUserTask task = new GetUserTask(db);
        return task.execute(name).get();

    }

    public void createGroup(Integer teacherId, String groupName) throws ExecutionException, InterruptedException {

        CreateGroupTask task = new CreateGroupTask(db);
        task.execute(String.valueOf(teacherId), groupName).get();

    }

    public List<String> getGroups(Integer userId) throws ExecutionException, InterruptedException {

        GetGroupsTask task = new GetGroupsTask(db);
        return task.execute(String.valueOf(userId)).get();

    }

    public void joinGroupAsStudent(Integer studentId, String groupName) throws ExecutionException, InterruptedException {

        AddStudentToGroupTask task = new AddStudentToGroupTask(db);
        task.execute(String.valueOf(studentId), groupName).get();

    }

    public void joinGroupAsTeacher(Integer teacherId, String groupName) throws ExecutionException, InterruptedException {

        AddTeacherToGroupTask task = new AddTeacherToGroupTask(db);
        task.execute(String.valueOf(teacherId), groupName).get();

    }

    public List<String> teacherInGroups(Integer userId) throws ExecutionException, InterruptedException {

        GetGroupsWithTeacherTask task = new GetGroupsWithTeacherTask(db);
        return task.execute(String.valueOf(userId)).get();

    }

    public List<String> getLessons(String groupName) throws ExecutionException, InterruptedException {

        GetLessonsTask task = new GetLessonsTask(db);
        return task.execute(groupName).get();

    }

    public Lesson getLesson(String group, String title) throws ExecutionException, InterruptedException {

        GetLessonTask task = new GetLessonTask(db);
        return task.execute(group, title).get();

    }

    public Lesson createLesson(String group, String title) throws ExecutionException, InterruptedException {

        CreateLessonTask task = new CreateLessonTask(db);
        return task.execute(group, title).get();

    }

    public void clear() throws ExecutionException, InterruptedException {

        // CASCADE should clear the student- and teacher-group tables, too.

        ClearUsersTask clearUsers = new ClearUsersTask(db);
        ClearGroupsTask clearGroups = new ClearGroupsTask(db);
//        ClearStudentGroupsTask clearStudentGroups = new ClearStudentGroupsTask(db);
//        ClearTeacherGroupsTask clearTeacherGroups = new ClearTeacherGroupsTask(db);
        ClearLessonsTask clearLessons = new ClearLessonsTask(db);
        ClearDictTask clearDict = new ClearDictTask(db);
        clearUsers.execute().get();
        clearGroups.execute().get();
//        clearStudentGroups.execute().get();
//        clearTeacherGroups.execute().get();
        clearLessons.execute().get();
        clearDict.execute().get();

    }

    public void close() {

        db.close();
        AppDatabase.destroyInstance();

    }

    private static class CreateUserTask extends AsyncTask<String, Void, User> {

        private final AppDatabase db;

        private CreateUserTask(AppDatabase db) { this.db = db; }

        @Override
        protected User doInBackground(String... strings) {

            // args : user_name, user_pass

            // Create a new user.
            User user = new User(strings[0], strings[1]);
            db.userDao().insertUser(user);
            return user;

        }

    }

    private static class GetUserTask extends AsyncTask<String, Void, User> {

        private final AppDatabase db;

        private GetUserTask(AppDatabase db) { this.db = db; }

        @Override
        protected User doInBackground(String... strings) {

            // args : user_name

            return db.userDao().getUser(strings[0]);

        }

    }

    private static class ClearUsersTask extends AsyncTask<Void, Void, Void> {

        private final AppDatabase db;

        private ClearUsersTask(AppDatabase db) { this.db = db; }

        @Override
        protected Void doInBackground(Void... voids) {

            List<User> users = db.userDao().getUsers();
            db.userDao().deleteUsers(users);

            return null;

        }

    }

    private static class CreateGroupTask extends AsyncTask<String, Void, Group> {

        private final AppDatabase db;

        private CreateGroupTask(AppDatabase db) { this.db = db; }

        @Override
        protected Group doInBackground(String... strings) {

            // args : user_id, group_name

            // Check if a group with the same name already exists.
            Group group = db.groupDao().getGroupFromName(strings[1]);

            if (group != null) return group;

            // Create the new group.
            group = new Group(strings[1]);
            db.groupDao().createGroup(group);

            // Mark the user as a teacher of the group.
            TeacherAndGroup teacherAndGroup = new TeacherAndGroup(Integer.parseInt(strings[0]), strings[1]);
            db.teacherAndGroupDao().createTeacherAndGroup(teacherAndGroup);

            return group;

        }

    }

    private static class GetGroupsTask extends AsyncTask<String, Void, List<String>> {

        private final AppDatabase db;

        private GetGroupsTask(AppDatabase db) { this.db = db; }

        @Override
        protected List<String> doInBackground(String... strings) {

            // args : user_id

            // Get all groups the user is a student or a teacher in.
            List<String> groups = db.studentAndGroupDao().getGroups(Integer.parseInt(strings[0]));
            groups.addAll(db.teacherAndGroupDao().getGroups(Integer.parseInt(strings[0])));

            return groups;

        }

    }

    private static class ClearGroupsTask extends AsyncTask<Void, Void, Void> {

        private final AppDatabase db;

        private ClearGroupsTask(AppDatabase db) { this.db = db; }

        @Override
        protected Void doInBackground(Void... voids) {

            // Delete all groups.
            List<Group> groups = db.groupDao().getGroups();
            db.groupDao().deleteGroups(groups);

            return null;

        }

    }

    private static class AddStudentToGroupTask extends AsyncTask<String, Void, String> {

        private final AppDatabase db;

        private AddStudentToGroupTask(AppDatabase db) { this.db = db; }

        @Override
        protected String doInBackground(String... strings) {

            // args : user_id, group_name

            // Check that group exists.
            Group group = db.groupDao().getGroupFromName(strings[1]);
            if (group == null) return null;

            StudentAndGroup studentAndGroup = new StudentAndGroup(Integer.parseInt(strings[0]), strings[1]);
            db.studentAndGroupDao().createStudentGroup(studentAndGroup);

            return strings[0];

        }

    }

    private static class ClearStudentGroupsTask extends AsyncTask<Void, Void, Void> {

        private final AppDatabase db;

        private ClearStudentGroupsTask(AppDatabase db) { this.db = db; }

        @Override
        protected Void doInBackground(Void... voids) {

            // Delete all student connections.
            List<StudentAndGroup> studentGroups = db.studentAndGroupDao().getStudentGroups();
            db.studentAndGroupDao().removeStudentGroups(studentGroups);

            return null;

        }

    }

    private static class AddTeacherToGroupTask extends AsyncTask<String, Void, String> {

        private final AppDatabase db;

        private AddTeacherToGroupTask(AppDatabase db) { this.db = db; }

        @Override
        protected String doInBackground(String... strings) {

            // args : user_id, group_name

            // Check that group exists.
            Group group = db.groupDao().getGroupFromName(strings[1]);
            if (group == null) return null;

            // Add to group teachers.
            TeacherAndGroup teacherAndGroup = new TeacherAndGroup(Integer.parseInt(strings[0]), strings[1]);
            db.teacherAndGroupDao().createTeacherAndGroup(teacherAndGroup);

            return strings[0];

        }

    }

    private static class GetGroupsWithTeacherTask extends AsyncTask<String, Void, List<String>> {

        private final AppDatabase db;

        private GetGroupsWithTeacherTask(AppDatabase db) { this.db = db; }

        @Override
        protected List<String> doInBackground(String... strings) {

            // args : user_id

            return db.teacherAndGroupDao().getGroups(Integer.parseInt(strings[0]));

        }

    }

    private static class ClearTeacherGroupsTask extends AsyncTask<Void, Void, Void> {

        private final AppDatabase db;

        private ClearTeacherGroupsTask(AppDatabase db) { this.db = db; }

        @Override
        protected Void doInBackground(Void... voids) {

            // Delete all teacher connections.
            List<TeacherAndGroup> teacherGroups = db.teacherAndGroupDao().getTeacherGroups();
            db.teacherAndGroupDao().removeTeacherGroups(teacherGroups);

            return null;

        }

    }

    private static class CreateLessonTask extends AsyncTask<String, Void, Lesson> {

        private final AppDatabase db;

        private CreateLessonTask(AppDatabase db) { this.db = db; }

        @Override
        protected Lesson doInBackground(String... strings) {

            // args : group_name, lesson_title

            // Create a new lesson under [group] and [title].
            Lesson lesson = new Lesson(strings[0], strings[1]);
            db.lessonDao().createLesson(lesson);

            return lesson;

        }

    }

    private static class GetLessonsTask extends AsyncTask<String, Void, List<String>> {

        private final AppDatabase db;

        private GetLessonsTask(AppDatabase db) { this.db = db; }

        @Override
        protected List<String> doInBackground(String... strings) {

            // args : group_name

            // Find all lessons in group.
            return db.lessonDao().getLessonTitlesForGroup(strings[0]);

        }

    }

    private static class GetLessonTask extends AsyncTask<String, Void, Lesson> {

        private final AppDatabase db;

        private GetLessonTask(AppDatabase db) { this.db = db; }

        @Override
        protected Lesson doInBackground(String... strings) {

            // args : group_name, lesson_title

            return db.lessonDao().getLessonFromTitle(strings[0], strings[1]);

        }

    }

    private static class ClearLessonsTask extends AsyncTask<Void, Void, Void> {

        private final AppDatabase db;

        private ClearLessonsTask(AppDatabase db) { this.db = db; }

        @Override
        protected Void doInBackground(Void... voids) {

            List<Lesson> lessons = db.lessonDao().getLessons();
            db.lessonDao().deleteLessons(lessons);

            return null;

        }

    }

    private static class CreateDictEntryTask extends AsyncTask<String, Void, Dict> {

        private final AppDatabase db;

        private CreateDictEntryTask(AppDatabase db) { this.db = db; }

        @Override
        protected Dict doInBackground(String... strings) {

            // args : dict_word, dict_meaning

            Dict dict = new Dict(strings[0], strings[1]);
            Dict oldDict = db.dictDao().getWord(strings[0]);
            if (oldDict == null) db.dictDao().createWord(dict);
            else {

                dict.setMeaning(oldDict.getMeaning() + "\n" + strings[1]);
                db.dictDao().updateWord(dict);
            }
            return dict;

        }

    }

    private static class GetDictEntryTask extends AsyncTask<String, Void, Dict> {

        private final AppDatabase db;

        private GetDictEntryTask(AppDatabase db) { this.db = db; }

        @Override
        protected Dict doInBackground(String... strings) {
            return db.dictDao().getWord(strings[0]);
        }

    }

    private static class ClearDictTask extends AsyncTask<Void, Void, Void> {

        private final AppDatabase db;

        private ClearDictTask(AppDatabase db) { this.db = db; }

        @Override
        protected Void doInBackground(Void... voids) {

            List<Dict> dicts = db.dictDao().getDict();
            db.dictDao().removeDicts(dicts);

            return null;

        }

    }

}
