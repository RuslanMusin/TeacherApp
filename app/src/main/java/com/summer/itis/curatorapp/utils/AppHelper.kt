package com.summer.itis.curatorapp.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.R.drawable.student
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.user.Person
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.utils.Const.MAX_LENGTH
import com.summer.itis.curatorapp.utils.Const.MORE_TEXT
import com.summer.itis.curatorapp.utils.Const.OFFLINE_STATUS
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

//ОСНОВНОЙ КЛАСС HELPER приложения. ОТСЮДА БЕРЕМ ТЕКУЩЕГО ЮЗЕРА ИЗ БД, ГРУЗИМ ФОТКУ ЮЗЕРА В ПРОФИЛЬ,
//ПОЛУЧАЕМ ССЫЛКУ НА ПУТЬ ФАЙЛОГО ХРАНИЛИЩА И СОЗДАЕМ СЕССИЮ. ПОКА ТАК ПУСТЬ БУДЕТ
class AppHelper {

    companion object {

        lateinit var currentCurator: Curator

        lateinit var otherCurator: Curator

        var userInSession: Boolean = false

        var userStatus: String = OFFLINE_STATUS

        var onlineFunction: (() -> Unit)? = null

        var offlineFunction: (() -> Unit)? = null


      /*  val fireAuth: FirebaseAuth = FirebaseAuth.getInstance()

        val storageReference: StorageReference
            get() = FirebaseStorage.getInstance().reference

        val dataReference: DatabaseReference = FirebaseDatabase.getInstance().reference*/

        fun setUserPhoto(photoView: ImageView, curator: Person, context: Context) {
            if (curator.isStandartPhoto) {
                Glide.with(context)
                        .load(R.drawable.teacher)
                        .into(photoView)
              /*  val imageReference = curator.photoUrl?.let { AppHelper.storageReference.child(it) }

                Log.d(TAG_LOG, "name " + (imageReference?.path ?: ""))

                Glide.with(context)
                        .load(imageReference)
                        .into(photoView)*/
            } else {
                Glide.with(context)
                        .load(curator.photoUrl)
                        .into(photoView)
            }
        }

        fun getLevelStr(level: Int, context: Context): String {
            var levelStr: String = context.getString(R.string.low_level)
            when(level) {
                0 -> levelStr = context.getString(R.string.low_level)

                1 -> levelStr = context.getString(R.string.medium_level)

                2 -> levelStr = context.getString(R.string.high_level)
            }
            return levelStr
        }

        fun getLevelInt(levelStr: String, context: Context): Int {
            var level: Int = 0
            when(levelStr) {
                context.getString(R.string.low_level) -> level = 0

                context.getString(R.string.medium_level) -> level = 1

                context.getString(R.string.high_level) -> level = 2
            }
            return level
        }

        fun saveCurrentState(curator: Curator, context: Context) {
            val editor = context.getSharedPreferences(curator.email, MODE_PRIVATE).edit()
            val curatorJson = gsonConverter.toJson(curator)
            editor.putString(USER_KEY, curatorJson)
            editor.apply()
        }

        fun setCurrentState(email: String, context: Context) {
            val prefs = context.getSharedPreferences(email, MODE_PRIVATE)
            val curatorJson = prefs.getString(USER_KEY, "");
            if(!curatorJson.equals("")){
                AppHelper.currentCurator = gsonConverter.fromJson(curatorJson, Curator::class.java)
            }
        }
/*

        fun setPhotoAndListener(photoView: ImageView, curator: Curator, context: Context) {
            setUserPhoto(photoView,curator,context)
            photoView.setOnClickListener { CuratorActivity.start(context, curator) }
        }
*/


        fun initUserState(application: Application) {
            /*val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                val user = firebaseAuth.currentCurator
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    Log.d(TAG_LOG, "logout")
                    LoginActivity.start(application)
                } else {
                    Log.d(TAG_LOG, "try to login")
                    val reference = RepositoryProvider.userRepository?.readUser(UserRepository.currentId)
                    reference?.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val user = dataSnapshot.getValue(Curator::class.java)
                            user?.let {
                                currentCurator = it
                                userInSession = true
                            }
                            Log.d(TAG_LOG,"user in session = ${currentCurator?.name}")
                            LoginActivity.start(application.applicationContext)
                            PersonalActivity.start(application.applicationContext, currentCurator)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {

                        }
                    })
                }
            }*/
        }

        fun readFileFromAssets(fileName: String, context: Context): List<String> {
            var reader: BufferedReader? = null
            var names: MutableList<String> = ArrayList()
            try {
                reader = BufferedReader(
                        InputStreamReader(context.assets.open(fileName), "UTF-8"))
                var mLine: String? = reader.readLine()
                while (mLine != null && !"".equals(mLine)) {
                    names.add(mLine)
                    mLine = reader.readLine()
                }
                return names
            } catch (e: IOException) {
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        //log the exception
                    }

                }
            }
            return names
        }

        fun convertDpToPx(dp: Float, context: Context): Int {
            val r = context.getResources()
            val px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dp,
                    r.getDisplayMetrics()
            ).toInt()
            return px
        }

        fun hideKeyboardFrom(context: Context, view: View) {
            Log.d(TAG_LOG,"hide keyboard")
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }

        fun showKeyboard(context: Context, editText: EditText) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }

        fun cutLongDescription(description: String, maxLength: Int): String {
            return if (description.length < MAX_LENGTH) {
                description
            } else {
                description.substring(0, MAX_LENGTH - MORE_TEXT.length) + MORE_TEXT
            }
        }

        private fun getListString(list: List<String>): String {
            var listString: String = ""
            for((i,item) in list.withIndex()) {
                listString += item
                if(i != list.lastIndex) {
                    listString += " , "
                }

            }
            listString.removeSuffix(",")
            return listString
        }

        public fun getSkillsList(context: Context): List<Skill> {
            val skills: MutableList<Skill> = ArrayList()
            val skillNames = listOf("Machine Learning", "Java", "Android SDK", "iOS", "Javascript", "C#", "C++", "OpenMP", "Kotlin", "UnitTest")
            for(i in 0..9) {
                val skill = Skill()
                skill.id = skillNames[i]
                skill.name = skillNames[i]
                skill.level = getLevelStr(i % 3, context)
                skills.add(skill)
            }
            return skills
        }

        public fun getMySkillList(context: Context): List<Skill> {
            val skills: MutableList<Skill> = ArrayList()
            val skillNames = listOf("Machine Learning", "Java", "Android SDK")
            for(i in 0..9) {
                val skill = Skill()
                skill.id = "$skillNames[i] $i"
                skill.name = skillNames[i % 3] + "$i"
                skill.level = getLevelStr(i % 3, context)
                skills.add(skill)
            }
            return skills
        }

        fun getThemeList(curator: Curator): List<Theme> {
            val themeList: MutableList<Theme> = ArrayList()
            val studentList = getStudentList()
            for (i in 0..9) {
                val theme = Theme()
                theme.id = "$i"
                theme.curator = curator
                theme.curatorId = curator.id
                theme.student = studentList[i]
                theme.description = "Simple App for students"
                if(i % 2 == 0) {
                    theme.title = "Web-платформа для создания интеллектуальных систем $i"
                    val subject = Subject()
                    subject.name = "Интеллектуальные системы"
                    subject.id = "$i"
                    theme.subject = subject
                    theme.subjectId = subject.id
                } else {
                    theme.title = "Приложение для обмена книгами $i"
                    val subject = Subject()
                    subject.name = "Android"
                    subject.id = "$i"
                    theme.subject = subject
                    theme.subjectId = subject.id
                }
                themeList.add(theme)
            }
            return themeList
        }

        fun getStudentList(): List<Student> {
            val idList: MutableList<String> = ArrayList()
            for(i in 1..10) {
                idList.add("$i")
            }

            val nameList: List<String> = listOf("Ruslan", "Marat", "Azat", "Aigul", "Rustem",
                    "Valery", "Rinat", "Ruzalin", "Gleb", "Ilshat")
            val lastNameList: List<String> = listOf("Musin", "Galimzanov", "Alekbaev", "Nurgatina",
                    "Galimov", "Petrov", "Muhametzyanov", "Khaibriev", "Mitko", "Rizvanov")

            val patronymicList: MutableList<String> = ArrayList()
            for(i in 1.. 10) {
                patronymicList.add("Azatovich")
            }

            val studentList: MutableList<Student> = ArrayList()
            for(i in 0..9) {
                val student = Student()
                student.id = idList[i]
                student.name = nameList[i]
                student.lastname = lastNameList[i]
                student.patronymic = patronymicList[i]

                when (i % 4) {

                    0 -> {
                        student.groupNumber = "11-601"
                        student.year = 1
                    }

                    1 -> {
                        student.groupNumber = "11-602"
                        student.year = 2
                    }

                    2 -> {
                        student.groupNumber = "11-603"
                        student.year = 3
                    }

                    3 -> {
                        student.groupNumber = "11-604"
                        student.year = 4
                    }
                }
                studentList.add(student)
            }

            return studentList

        }

        fun getFakeStudent(number: Int, context: Context): Student {
            val student: Student = Student()
            when (number) {

                0 -> {
                    student.year = 1
                    student.groupNumber = "11-603"
                    student.name = "Ruslan"
                    student.lastname = "Nusin"
                    student.patronymic  = "Maratovich"
                    student.skills = getSkillsList(context).subList(0, 3).toMutableList()
                }

                1 -> {
                    student.year = 2
                    student.groupNumber = "11-602"
                    student.name = "Valery"
                    student.lastname = "Petrov"
                    student.patronymic  = "Rinatovich"
                    student.skills = getSkillsList(context).subList(3, 5).toMutableList()
                }

                2 -> {
                    student.year = 3
                    student.groupNumber = "11-605"
                    student.name = "Rinat"
                    student.lastname = "Muhametzyanov"
                    student.patronymic  = "Azatovich"
                    student.skills = getSkillsList(context).subList(6, 9).toMutableList()
                }

            }

            return student
        }

    }




}
