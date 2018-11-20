package com.summer.itis.curatorapp.ui.base.navigation_base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.ui.base.base_first.activity.BaseActivity
import com.summer.itis.curatorapp.utils.AppHelper
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R.id.bottom_navigation
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAG_NAVIG_ACT
import com.summer.itis.curatorapp.ui.curator.curator_item.view.CuratorFragment
import com.summer.itis.curatorapp.ui.student.student_list.StudentListFragment
import com.summer.itis.curatorapp.ui.theme.theme_list.ThemeListFragment
import com.summer.itis.curatorapp.ui.work.work_list.WorkListFragment
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_theme_tabs.*
import java.util.*
import kotlin.collections.HashMap


//АКТИВИТИ РОДИТЕЛЬ ДЛЯ ОСНОВНОЙ НАВИГАЦИИ(БОКОВОЙ). ЮЗАТЬ МЕТОДЫ supportActionBar И setBackArrow(ЕСЛИ НУЖНА СТРЕЛКА НАЗАД)
class NavigationBaseActivity : BaseActivity<NavigationPresenter>(), NavigationView {

    @InjectPresenter
    override lateinit var presenter: NavigationPresenter

    private lateinit var mStacks: HashMap<String, Stack<Fragment>>
    private lateinit var relativeTabs: HashMap<String, String>

    private var mCurrentTab: String? = null
    private var mShowTab: String? = null

    private var editFragment: Fragment? = null

    companion object {

        const val TAG_NAVIG_ACT = "TAG_NAVIG_ACTIVITY"

        const val TAB_PROFILE = "tab_profile"
        const val TAB_STUDENTS = "tab_students"
        const val TAB_THEMES = "tab_themes"
        const val TAB_WORKS = "tab_works"
        const val SHOW_PROFILE = "show_profile"
        const val SHOW_THEMES = "show_theme"
        const val SHOW_WORKS = "show_works"

        fun start(context: Context) {
            val intent = Intent(context, NavigationBaseActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        initBottomNavigation()

        mStacks = HashMap()
        mStacks[TAB_PROFILE] = Stack()
        mStacks[TAB_STUDENTS] = Stack()
        mStacks[TAB_THEMES] = Stack()
        mStacks[TAB_WORKS] = Stack()
        mStacks[SHOW_PROFILE] = Stack()
        mStacks[SHOW_THEMES] = Stack()
        mStacks[SHOW_WORKS] = Stack()

        relativeTabs = HashMap()
        relativeTabs[TAB_PROFILE] = SHOW_PROFILE
        relativeTabs[TAB_THEMES] = SHOW_THEMES
        relativeTabs[TAB_WORKS] = SHOW_WORKS

        bottom_navigation.selectedItemId = R.id.action_works

     /*   val args: Bundle = Bundle()
        val userJson = gsonConverter.toJson(AppHelper.currentCurator)
        args.putString(USER_KEY, userJson)
        loadFragment(CuratorFragment.newInstance(args, this))*/
       /* val contentFrameLayout = findViewById<FrameLayout>(R.id.container)
        layoutInflater.inflate(getContentLayout(), contentFrameLayout)*/
    }

    override fun supportActionBar(toolbar: Toolbar) {
        if(supportActionBar == null) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
//            initBottomNavigation()
        }
    }

    private fun initBottomNavigation() {
        val bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.isItemHorizontalTranslationEnabled = false
        bottomNavigationView.setOnNavigationItemSelectedListener(
                object : BottomNavigationView.OnNavigationItemSelectedListener {
                    override fun onNavigationItemSelected(item: MenuItem): Boolean {
                        when (item.getItemId()) {
                            R.id.action_profile -> {
                                selectedTab(TAB_PROFILE)
                            }

                            R.id.action_works -> {
//                                loadFragment(StudentListFragment.newInstance(this@NavigationBaseActivity))
                                selectedTab(TAB_WORKS)
                            }

                            R.id.action_themes -> {
                                selectedTab(TAB_THEMES)
                            }
                        }
                        return true
                    }
                })
    }

    private fun selectedTab(tabId: String) {
        mCurrentTab = tabId
        mShowTab = relativeTabs[tabId]
        val size = mStacks[tabId]?.size
        setToolbar(null)
        if (size == 0) {
            when(tabId) {

                TAB_PROFILE -> showProfile(tabId)

                TAB_STUDENTS -> showStudents(tabId)

                TAB_THEMES -> showThemes(tabId)

                TAB_WORKS -> showWorks(tabId)
            }
        } else {
            if(mStacks[mShowTab]?.size == 0) {
                mStacks[tabId]?.lastElement()?.let {
                    pushFragments(tabId, it, false)
                }
            } else {
                mStacks[mShowTab]?.lastElement()?.let {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.container, it)
                         ft.commit()
                }
            }
        }
    }

    override fun pushFragments(tag: String, fragment: Fragment, shouldAdd: Boolean) {
        showBottomNavigation()
        if (shouldAdd) {
            mStacks[tag]?.push(fragment)
        }
        val manager = supportFragmentManager
//        hideBottomNavigation()
        val ft = manager.beginTransaction()
        ft.replace(R.id.container, fragment)
        ft.commit()
//        showBottomNavigation()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        showBottomNavigation()
        if(mStacks[mShowTab]?.size != 0) {
            hideFragment()
        } else {
            popCurrentFragment()
        }
       /* when(mCurrentTab) {

            TAB_THEMES -> popCurrentFragment()

            TAB_PROFILE -> popCurrentFragment()

            TAB_WORKS -> popCurrentFragment()

            SHOW_THEMES -> hideFragment()

            SHOW_PROFILE -> hideFragment()

            SHOW_WORKS -> hideFragment()
        }*/

        /*if(mStacks[SHOW_PROFILE]?.size != 0) {
            editFragment?.let { hideFragment() }
        } else {
        }*/
    }

    private fun popCurrentFragment() {
        val size = mStacks.get(mCurrentTab)?.size
        if(size == 1){
            finish();
            return;
        }
        popFragments();
    }

    fun popFragments() {
        val fragment = mStacks[mCurrentTab]?.size?.minus(2)?.let { mStacks[mCurrentTab]?.elementAt(it) }

        mStacks[mCurrentTab]?.pop()

        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        fragment?.let { ft.replace(R.id.container, it) }
        ft.commit()
    }

    override fun hideFragment() {
        val fragment = mStacks[mShowTab]?.size?.minus(2)?.let { mStacks[mShowTab]?.elementAt(it) }

        val fragmentBefore = mStacks[mShowTab]?.pop()
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        fragmentBefore?.let { ft.hide(it).remove(fragmentBefore) }
        val size = mStacks.get(mShowTab)?.size
        if(size!! == 1){
            mStacks[mShowTab]?.pop()
        }
        fragment?.let { ft.show(it) }

        ft.commit()
    }

    override fun showFragment(tabId: String, lastFragment: Fragment, fragment: Fragment) {
        if(mStacks[mShowTab]?.size == 0) {
            mStacks[mShowTab]?.push(lastFragment)
        }
        mStacks[mShowTab]?.push(fragment)
//        editFragment = fragment
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        lastFragment.let {ft.hide(it).add(R.id.container, fragment).show(fragment)}
        ft.commit()
    }



    private fun showProfile(tabId: String) {
        Log.d(TAG_NAVIG_ACT, "curator start")
        val args: Bundle = Bundle()
        val userJson = gsonConverter.toJson(AppHelper.currentCurator)
        args.putString(USER_KEY, userJson)
        val fragment = CuratorFragment.newInstance(args, this)
        pushFragments(tabId, fragment, true)
    }



    private fun showStudents(tabId: String) {
        val fragment = StudentListFragment.newInstance(this)
        pushFragments(tabId, fragment, true)
    }

    private fun showThemes(tabId: String) {
        val fragment = ThemeListFragment.newInstance(this)
        pushFragments(tabId, fragment, true)
    }

    private fun showWorks(tabId: String) {
        val fragment = WorkListFragment.newInstance(this)
        pushFragments(tabId, fragment, true)
    }

    override fun hideBottomNavigation() {
        bottom_navigation.visibility = View.GONE
    }

    override fun showBottomNavigation() {
        bottom_navigation.visibility = View.VISIBLE
        changeWindowsSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    override fun changeWindowsSoftInputMode(mode: Int) {
        this.window.setSoftInputMode(mode);
    }

    override fun setToolbar(toolbar: Toolbar?) {
        super.setToolbar(toolbar)
       /* bottom_navigation.refreshDrawableState()
        container.refreshDrawableState()*/
    }

}
