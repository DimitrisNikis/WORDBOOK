package com.example.wordbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clearBackStack()

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_home -> {
                    replaceFragment(MainMenuFragment())
                    true
                }
                R.id.menu_item_add -> {
                    replaceFragment(AddWordFragment())
                    true
                }
                R.id.menu_item_test -> {
                    replaceFragment(TestChooseFragment())
                    true
                }
                else -> false
            }
        }

        if (intent.hasExtra("correctAnswers") && intent.hasExtra("totalQuestions")) {
            val correctAnswers = intent.getIntExtra("correctAnswers", 0)
            val totalQuestions = intent.getIntExtra("totalQuestions", 0)
            showResultsFragment(correctAnswers, totalQuestions)
        } else {
            replaceFragment(MainMenuFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun clearBackStack() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            val firstFragment = fragmentManager.getBackStackEntryAt(0)
            fragmentManager.popBackStack(firstFragment.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    private fun showResultsFragment(correctAnswers: Int, totalQuestions: Int) {
        val fragment = ResultsFragment.newInstance(correctAnswers, totalQuestions)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
