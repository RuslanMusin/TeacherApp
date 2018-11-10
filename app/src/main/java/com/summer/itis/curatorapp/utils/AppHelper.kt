package com.summer.itis.curatorapp.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.R.string.level
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.user.Person
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
}
