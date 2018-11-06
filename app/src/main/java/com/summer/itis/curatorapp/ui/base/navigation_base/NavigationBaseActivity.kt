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
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.ui.curator.curator_item.view.CuratorFragment
import com.summer.itis.curatorapp.ui.student.student_list.StudentListFragment
import com.summer.itis.curatorapp.ui.theme.theme_list.ThemeListFragment
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.activity_base.*
import java.util.*






//АКТИВИТИ РОДИТЕЛЬ ДЛЯ ОСНОВНОЙ НАВИГАЦИИ(БОКОВОЙ). ЮЗАТЬ МЕТОДЫ supportActionBar И setBackArrow(ЕСЛИ НУЖНА СТРЕЛКА НАЗАД)
class NavigationBaseActivity : BaseActivity<NavigationPresenter>(), NavigationView {

    @InjectPresenter
    override lateinit var presenter: NavigationPresenter

    private lateinit var mStacks: HashMap<String, Stack<Fragment>>

    private var mCurrentTab: String? = null

    private var editFragment: Fragment? = null

    companion object {

        const val TAG_NAVIG_ACT = "TAG_NAVIG_ACTIVITY"

        const val TAB_PROFILE = "tab_profile"
        const val TAB_STUDENTS = "tab_students"
        const val TAB_THEMES = "tab_themes"
        const val TAB_WORKS = "tab_works"
        const val LAST_FRAGMENTS = "last"

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
        mStacks[LAST_FRAGMENTS] = Stack()

        bottom_navigation.selectedItemId = R.id.action_profile

     /*   val args: Bundle = Bundle()
        val userJson = gsonConverter.toJson(AppHelper.currentCurator)
        args.putString(USER_KEY, userJson)
        loadFragment(CuratorFragment.newInstance(args, this))*/
       /* val contentFrameLayout = findViewById<FrameLayout>(R.id.container)
        layoutInflater.inflate(getContentLayout(), contentFrameLayout)*/
    }

    override fun loadFragment(fragment: Fragment) {
        //switching fragment
        /*supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()*/
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
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

                            R.id.action_students -> {
//                                loadFragment(StudentListFragment.newInstance(this@NavigationBaseActivity))
                                selectedTab(TAB_STUDENTS)
                            }

                            R.id.action_themes -> {
                                selectedTab(TAB_THEMES)
                            }
                        }
                        return true
                    }
                })
    }

    private fun gotoFragment(selectedFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, selectedFragment)
        fragmentTransaction.commit()
    }

    private fun selectedTab(tabId: String) {
        mCurrentTab = tabId
        val size = mStacks[tabId]?.size
        if (size == 0) {
            when(tabId) {

                TAB_PROFILE -> showProfile(tabId)

                TAB_STUDENTS -> showStudents(tabId)

                TAB_THEMES -> showThemes(tabId)

                TAB_WORKS -> showWorks(tabId)
            }
        } else {
            mStacks[tabId]?.lastElement()?.let { pushFragments(tabId, it, false) }
        }
    }

    override fun pushFragments(tag: String, fragment: Fragment, shouldAdd: Boolean) {
        showBottomNavigation()
        if (shouldAdd)
            mStacks[tag]?.push(fragment)
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        ft.replace(R.id.container, fragment)
        ft.commit()
    }

    override fun setResultAndBack(args: Bundle) {
      /*  val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        editableFragment?.let {

            ft.replace(R.id.container, it) }
        ft.commit()*/
    }

    override fun openFragmentForEdit(currentFragment: Fragment, editfragment: Fragment) {
      /*  this.editableFragment = currentFragment
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        ft.replace(R.id.container, editfragment)
        ft.commit()*/
    }

    fun popFragments() {
        val fragment = mStacks[mCurrentTab]?.size?.minus(2)?.let { mStacks[mCurrentTab]?.elementAt(it) }

        mStacks[mCurrentTab]?.pop()

        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        fragment?.let { ft.replace(R.id.container, it) }
        ft.commit()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        showBottomNavigation()
        val size = mStacks.get(mCurrentTab)?.size
        if(size == 1){
            finish();
            return;
        }
        if(mStacks[LAST_FRAGMENTS]?.size != 0) {
            editFragment?.let { hideFragment(it) }
        } else {
            popFragments();
        }
    }

    private fun showProfile(tabId: String) {
        Log.d(TAG_NAVIG_ACT, "curator start")
        val args: Bundle = Bundle()
        val userJson = gsonConverter.toJson(AppHelper.currentCurator)
        args.putString(USER_KEY, userJson)
        val fragment = CuratorFragment.newInstance(args, this)
        pushFragments(tabId, fragment, true)

//        loadFragment()
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

    }

    override fun showFragment(lastFragment: Fragment, fragment: Fragment) {
        mStacks[LAST_FRAGMENTS]?.push(lastFragment)
        editFragment = fragment
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        lastFragment.let {ft.hide(it).add(R.id.container, fragment).show(fragment)}
        ft.commit()
    }

    override fun hideFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        mStacks[LAST_FRAGMENTS]?.pop()?.let { ft.hide(fragment).remove(fragment).show(it) }
        ft.commit()
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
}
