package com.example.newpeopletest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)
        viewPager2.adapter = MyAdapter(this)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout,viewPager2){tab, position->
            tab.text = "Fragment$position"
        }.attach()
    }
}

class MyAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if(position==0)
            BlankFragment1()
        else
            BlankFragment2()
    }

}