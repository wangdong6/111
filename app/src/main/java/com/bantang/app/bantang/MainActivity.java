package com.bantang.app.bantang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bantang.app.bantang.fragment.FindFragment;
import com.bantang.app.bantang.fragment.MineFragment;
import com.bantang.app.bantang.fragment.MsgFragment;
import com.bantang.app.bantang.fragment.TitleFragment;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    private RadioGroup radiogroup;
    private RadioButton rbt_title;
    private RadioButton rbt_find;
    private RadioButton rbt_msg;
    private RadioButton rbt_mine;
    private ImageView iv_add;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private ArrayList<Fragment> fragemntlist;
    BottomNavigationBar  bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initviewnav();
       // initview();
        fragemntlist = new ArrayList<Fragment>();
        fragemntlist.add(new TitleFragment());
        fragemntlist.add(new FindFragment());
        fragemntlist.add(new MsgFragment());
        fragemntlist.add(new MineFragment());

        initFragment();
    }

    private void initFragment() {
        FragmentManager manager=getSupportFragmentManager();

        FragmentTransaction transaction=manager.beginTransaction();

        transaction.add(R.id.framelayout,fragemntlist.get(0));

        transaction.commit();

        bottom_nav.setTabSelectedListener(this);
    }

    /**
     * 初始化底部导航
     */
    private void initviewnav() {
        bottom_nav = (BottomNavigationBar) this.findViewById(R.id.bottom_nav);
        bottom_nav.setMode(BottomNavigationBar.MODE_FIXED);
        bottom_nav.setActiveColor("#FD6363");
        bottom_nav.setInActiveColor("#cccccc");
        bottom_nav.addItem(new BottomNavigationItem(R.drawable.ic_main_tab_home_checked_fuben2,"首页").setInactiveIcon(this.getResources().getDrawable(R.drawable.ic_main_tab_home)))
                .addItem(new BottomNavigationItem(R.drawable.ic_main_tab_community_checked_fuben,"发现").setInactiveIcon(this.getResources().getDrawable(R.drawable.ic_main_tab_community)))
                .addItem(new BottomNavigationItem(R.drawable.ic_main_tab_msg_checked_fuben,"消息").setInactiveIcon(this.getResources().getDrawable(R.drawable.ic_main_tab_msg)))
                .addItem(new BottomNavigationItem(R.drawable.ic_main_tab_personal_checked,"我的").setInactiveIcon(this.getResources().getDrawable(R.drawable.ic_main_tab_personal)))
                .initialise();
    }

    /**
     * 选中
     * @param position
     */
    @Override
    public void onTabSelected(int position) {
        if(fragemntlist!=null){
            if(position<fragemntlist.size()){
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                Fragment fragment = fragemntlist.get(position);
                if(fragment.isAdded()){
                    fragmentTransaction.show(fragment);
                }else{
                    fragmentTransaction.add(R.id.framelayout,fragment);
                }
                fragmentTransaction.commitAllowingStateLoss();
            }
        }
    }

    /**
     * 未选中
     *
     * @param position
     */
    @Override
    public void onTabUnselected(int position) {
        if(fragemntlist!=null){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            Fragment fragment = fragemntlist.get(position);
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onTabReselected(int position) {

    }



    /*//初始化视图
    private void initview() {
        //初始化图片的添加
        iv_add = (ImageView) this.findViewById(R.id.iv_add);
        radiogroup = (RadioGroup) this.findViewById(R.id.radiogroup);
        rbt_title = (RadioButton) this.findViewById(R.id.rbt_title);
        rbt_find = (RadioButton) this.findViewById(R.id.rbt_find);
        rbt_msg = (RadioButton) this.findViewById(R.id.rbt_msg);
        rbt_mine = (RadioButton) this.findViewById(R.id.rbt_mine);
        //默认选中
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        TitleFragment titlefragment  = new TitleFragment();
        transaction.replace(R.id.framelayout,titlefragment);
        transaction.commit();
        //底部菜单
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch(checkId){
                    case R.id.rbt_title:
                        transaction = manager.beginTransaction();
                        TitleFragment titlefragment  = new TitleFragment();
                        transaction.replace(R.id.framelayout,titlefragment);
                        transaction.commit();
                        break;
                    case R.id.rbt_find:
                        transaction = manager.beginTransaction();
                        FindFragment findfragment  = new FindFragment();
                        transaction.replace(R.id.framelayout,findfragment);
                        transaction.commit();
                        break;
                    case R.id.rbt_msg:
                        transaction = manager.beginTransaction();
                        MsgFragment msgfragment  = new MsgFragment();
                        transaction.replace(R.id.framelayout,msgfragment);
                        transaction.commit();
                        break;
                    case R.id.rbt_mine:
                        transaction = manager.beginTransaction();
                        MineFragment minefragment  = new MineFragment();
                        transaction.replace(R.id.framelayout,minefragment);
                        transaction.commit();
                        break;
                }
            }
        });
    }*/
}
