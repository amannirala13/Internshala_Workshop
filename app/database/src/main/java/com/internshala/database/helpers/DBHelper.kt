package com.internshala.database.helpers


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.internshala.database.models.Student
import com.internshala.database.models.Workshop

class DBHelper(var context:Context?):
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object{
        const val DB_NAME = "internshala_workshop.db"
        const val DB_VERSION = 1


        const val STUDENTS_TABLE = "students"
        const val STUDENT_COL_ID = "id"
        const val STUDENT_COL_NAME = "name"
        const val STUDENT_COL_EMAIL = "email"
        const val STUDENT_COL_PHONE = "phone"
        const val STUDENT_COL_PASSWORD = "password"

        const val WORKSHOP_TABLE = "workshop"
        const val WORKSHOP_COL_ID = "id"
        const val WORKSHOP_COL_TITLE = "title"
        const val WORKSHOP_COL_AGENDA = "agenda"
        const val WORKSHOP_COL_ORGANIZER = "organizer"
        const val WORKSHOP_COL_DURATION = "duration"
        const val WORKSHOP_COL_TAGS = "tags"

        const val ENROLLMENT_TABLE = "enroll"
        const val ENROLLMENT_COL_ID = "id"
        const val ENROLLMENT_COL_STUDENT_ID = "student_id"
        const val ENROLLMENT_COL_WORKSHOP_ID = "workshop_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createStudentTableQuery = "CREATE TABLE $STUDENTS_TABLE ( $STUDENT_COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $STUDENT_COL_NAME TEXT, $STUDENT_COL_EMAIL TEXT, $STUDENT_COL_PASSWORD TEXT, $STUDENT_COL_PHONE TEXT )"
        val createWorkshopTableQuery = "CREATE TABLE $WORKSHOP_TABLE ( $WORKSHOP_COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $WORKSHOP_COL_TITLE TEXT, $WORKSHOP_COL_AGENDA TEXT, $WORKSHOP_COL_ORGANIZER TEXT, $WORKSHOP_COL_DURATION INTEGER, $WORKSHOP_COL_TAGS TEXT )"
        val createEnrollmentTableQuery = "CREATE TABLE $ENROLLMENT_TABLE($ENROLLMENT_COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $ENROLLMENT_COL_STUDENT_ID INTEGER, $ENROLLMENT_COL_WORKSHOP_ID INTEGER )"
        if(db != null) {
            db.execSQL(createStudentTableQuery)
            db.execSQL(createWorkshopTableQuery)
            db.execSQL(createEnrollmentTableQuery)

            populateWorkshopData(db) // Populating data with hardcoded values
        }
        else{
            Toast.makeText(context, "Database error: null", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * This function hard codes workshop data into the database
     *
     * @param db [SQLiteDatabase] - SQLite database object
     * @return [Unit]
     */
    private fun populateWorkshopData(db: SQLiteDatabase) {
        db.beginTransaction()
        for(i in 1..50){
            val cv = ContentValues()
            cv.put(WORKSHOP_COL_TITLE, "Course $i")
            cv.put(WORKSHOP_COL_AGENDA, "Learn all $i course(s)")
            cv.put(WORKSHOP_COL_ORGANIZER, "Internshala")
            cv.put(WORKSHOP_COL_DURATION, i)
            cv.put(WORKSHOP_COL_TAGS, "python, ai, chat-bot")

            db.insert(WORKSHOP_TABLE, "null", cv)
        }
        db.setTransactionSuccessful();
        db.endTransaction()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        /**
         * TODO("Not yet implemented")
         * This function is used to handle database changes depending on the version.
         * We will not be implementing this method because this application is just for demo
         * purpose. You might want to look into this function's implementation if you are looking
         * forward to make a production level application.
         *
         */
    }


    /**
     * This function Writes new users data to database
     *
     * @see Student
     * @param student [Student] - Student data
     * @return Boolean - true if write was successful else false
     */
    fun registerStudent(student: Student): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(STUDENT_COL_NAME, student.name)
        contentValues.put(STUDENT_COL_PHONE, student.phone)
        contentValues.put(STUDENT_COL_EMAIL, student.email)
        contentValues.put(STUDENT_COL_PASSWORD, student.password)

        val result = db.insert(STUDENTS_TABLE, "null",contentValues)
        db.close()
        return result > 0
    }

    /**
     * Get all the student details from the database.
     *
     * Warning: This application is not secured with encryption.
     * This is a demo application with very limited security. Don't use this function or this application
     * in production as it might lead to extreme vulnerabilities and data leaks.
     *
     * @return [MutableList<Student>?]
     */
    fun getAllDataFromStudents():MutableList<Student>?{
        val studentsList = mutableListOf<Student>()

        val query = "SELECT * FROM $STUDENTS_TABLE"
        val db = this.readableDatabase

        val cursor = db.rawQuery(query, null)
        if(cursor.moveToFirst()){
            do studentsList.add(getStudentFromCursor(cursor))
            while(cursor.moveToNext())
        }else{
            return null
        }
        cursor.close()
        db.close()
        return studentsList
    }

    /**
     * This function gets all the workshop data from database and returns a mutable list of
     * [Workshop]
     *
     * @return [MutableList<Workshop>?] - Workshop list or null if the database is empty
     */
    fun getAllWorkshops():MutableList<Workshop>?{
        val workshopList = mutableListOf<Workshop>()

        val query = "SELECT * FROM $WORKSHOP_TABLE"
        val db = this.readableDatabase

        val cursor = db.rawQuery(query, null)
        if(cursor.moveToFirst()){
            do workshopList.add(getWorkshopFromCursor(cursor))
                while(cursor.moveToNext())
        }else return null
        return workshopList
    }

    /**
     * This function enrolls a student to a workshop by adding the data to enrollment table
     *
     * @param studentID Int
     * @param workshopID Int
     * @return [Boolean]
     */
    fun enrollToWorkshop(studentID: Int, workshopID: Int):Boolean{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(ENROLLMENT_COL_STUDENT_ID, studentID)
        cv.put(ENROLLMENT_COL_WORKSHOP_ID, workshopID)

        val result = db.insert(ENROLLMENT_TABLE, "null",cv)
        db.close()
        return result > 0
    }

    /**
     * This function returns the list of all the workshops a student is enrolled in
     *
     * @param studentID [Int] - The student id
     * @return [MutableList<Workshop>? ]- Enrolled workshop list
     */
    fun getStudentEnrollments(studentID: Int): MutableList<Workshop>?{
        val enrolledList = mutableListOf<Workshop>()
        val db = this.readableDatabase
        val query ="SELECT * FROM $WORKSHOP_TABLE " +
                "INNER JOIN $ENROLLMENT_TABLE " +
                "ON $WORKSHOP_TABLE.$WORKSHOP_COL_ID = $ENROLLMENT_TABLE.$ENROLLMENT_COL_WORKSHOP_ID " +
                "WHERE $ENROLLMENT_TABLE.$ENROLLMENT_COL_STUDENT_ID = $studentID"

        val cursor = db.rawQuery(query, null)
        return if(cursor.moveToFirst()){
            do enrolledList.add(getWorkshopFromCursor(cursor))
                while(cursor.moveToNext())
            cursor.close()
            db.close()
            enrolledList
        }else{
            cursor.close()
            db.close()
            null
        }
    }

    /**
     * This function deleted an enrollment of specific student from specific workshop
     * @param studentID Int
     * @param workshopID Int
     * @return Boolean
     */
    fun deleteEnrollment(studentID: Int, workshopID: Int): Boolean{
        val db = this.writableDatabase
        //val query = "DELETE FROM $ENROLLMENT_TABLE WHERE $ENROLLMENT_COL_STUDENT_ID = $studentID AND $ENROLLMENT_COL_WORKSHOP_ID = $workshopID"
        return db.delete(ENROLLMENT_TABLE,
            "$ENROLLMENT_COL_STUDENT_ID = ? AND $ENROLLMENT_COL_WORKSHOP_ID = ?",
            arrayOf(studentID.toString(), workshopID.toString())) > 0
    }

    /**
     * This function returns [Workshop] from databasing using workshop id
     *
     * @param workshopID [Int] - Workshop id
     * @return [Workshop?] - Workshop object
     */
    fun getWorkshop(workshopID: Int):Workshop?{
        val query = "SELECT * FORM $WORKSHOP_TABLE WHERE $WORKSHOP_COL_ID = $workshopID"
        val db = this.readableDatabase

        val cursor = db.rawQuery(query, null)
        return if(cursor.moveToFirst()){
            val workshop = getWorkshopFromCursor(cursor)
            cursor.close()
            db.close()
            workshop
        }else{
            cursor.close()
            db.close()
            null
        }
    }

    /**
     * This function gets the user by matching the email and password
     *
     * @param email [String] - User email address
     * @param password [String] - User password
     *
     * @return [Student?] - The student object is the credentials match
     */
    fun getLoginUser(email:String, password:String):Student?{
        val query = "SELECT * FROM $STUDENTS_TABLE WHERE $STUDENT_COL_EMAIL = '$email' AND $STUDENT_COL_PASSWORD = '$password'"
        val db = this.readableDatabase

        val cursor = db.rawQuery(query, null)
        return if(cursor.moveToFirst()){
            val student = getStudentFromCursor(cursor)
            cursor.close()
            db.close()
            student
        }else {
            cursor.close()
            db.close()
            null
        }
    }

    /**
     * This function checks the uniqueness of the email
     *
     * @param email [String] - User email address
     * @return [Boolean] - [true] if the email is unique else [false]
     */
    fun checkUniqueEmail(email: String): Boolean{
        val query = "SELECT * FROM $STUDENTS_TABLE WHERE $STUDENT_COL_EMAIL = '$email'"
        val db = this.readableDatabase

        val cursor = db.rawQuery(query, null)
        return if(cursor.moveToFirst()){
            cursor.close()
            db.close()
            false
        }else {
            cursor.close()
            db.close()
            true
        }
    }

    /**
     * This function returns [Student] object from cursor
     * @param cursor Cursor
     * @return [Student]
     */
    private fun getStudentFromCursor(cursor: Cursor): Student{
        val id = cursor.getInt(0)
        val name = cursor.getString(1)
        val email = cursor.getString(2)
        val password = cursor.getString(3)
        val phone = cursor.getString(4)

        return Student(id,name,phone,email,password)
    }

    /**
     * Get [Workshop] object from cursor object
     *
     * @param cursor [Cursor] - SQLite Cursor object
     * @return [Workshop] - Workshop object
     */
    private fun getWorkshopFromCursor(cursor: Cursor): Workshop{
        val id = cursor.getInt(0)
        val title = cursor.getString(1)
        val agenda = cursor.getString(2)
        val organizer = cursor.getString(3)
        val duration = cursor.getInt(4)
        val tags = cursor.getString(5)

        return Workshop(id,title,agenda,organizer,duration,tags)
    }
}