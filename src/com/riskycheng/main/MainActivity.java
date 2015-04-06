package com.riskycheng.main;

import java.util.ArrayList;
import java.util.List;

import com.riskycheng.fragment.Fragment_Page_A;
import com.riskycheng.fragment.Fragmnet_Page_B;
import com.riskycheng.fragment.Fragmnet_Page_C;
import com.riskycheng.main.R;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private ViewPager viewPager;// 页卡内容
	private ImageView imageView;// 动画图片
	private TextView Page_A, Page_B, Page_C;// 选项名称
	private List<Fragment> fragments;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private int selectedColor, unSelectedColor;
	/** 页卡总数 **/
	private static final int pageSize = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		initView();
	}

	private void initView() {
		selectedColor = getResources()
				.getColor(R.color.tab_title_pressed_color);
		unSelectedColor = getResources().getColor(
				R.color.tab_title_normal_color);

		InitImageView();
		InitTextView();
		InitViewPager();
	}

	/**
	 * 初始化Viewpager页
	 */
	private void InitViewPager() {
		viewPager = (ViewPager) findViewById(R.id.vPager);
		fragments = new ArrayList<Fragment>();
		fragments.add(new Fragment_Page_A());
		fragments.add(new Fragmnet_Page_B());
		fragments.add(new Fragmnet_Page_C());
		viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(),
				fragments));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 初始化头标
	 * 
	 */
	private void InitTextView() {
		Page_A = (TextView) findViewById(R.id.tab_1);
		Page_B = (TextView) findViewById(R.id.tab_2);
		Page_C = (TextView) findViewById(R.id.tab_3);
		

		Page_A.setTextColor(selectedColor);
		Page_B.setTextColor(unSelectedColor);
		Page_C.setTextColor(unSelectedColor);


		Page_A.setOnClickListener(new MyOnClickListener(0));
		Page_B.setOnClickListener(new MyOnClickListener(1));
		Page_C.setOnClickListener(new MyOnClickListener(2));
	}

	/**
	 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 */

	private void InitImageView() {
		imageView = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_selected_bg).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / pageSize - bmpW) / 2;// 计算偏移量--(屏幕宽度/页卡总数-图片实际宽度)/2
													// = 偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}

	/**
	 * 头标点击监听
	 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {

			switch (index) {
			case 0:
				Page_A.setTextColor(selectedColor);
				Page_B.setTextColor(unSelectedColor);
				Page_C.setTextColor(unSelectedColor);
				break;
			case 1:
				Page_B.setTextColor(selectedColor);
				Page_A.setTextColor(unSelectedColor);
				Page_C.setTextColor(unSelectedColor);
				break;
			case 2:
				Page_C.setTextColor(selectedColor);
				Page_A.setTextColor(unSelectedColor);
				Page_B.setTextColor(unSelectedColor);
				break;
			}
			viewPager.setCurrentItem(index);
		}

	}

	/**
	 * 为选项卡绑定监听器
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		public void onPageScrollStateChanged(int index) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int index) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* index, 0, 0);// 显然这个比较简洁，只有一行代码。
			currIndex = index;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);

			switch (index) {
			case 0:
				Page_A.setTextColor(selectedColor);
				Page_B.setTextColor(unSelectedColor);
				Page_C.setTextColor(unSelectedColor);
				break;
			case 1:
				Page_B.setTextColor(selectedColor);
				Page_A.setTextColor(unSelectedColor);
				Page_C.setTextColor(unSelectedColor);
				break;
			case 2:
				Page_C.setTextColor(selectedColor);
				Page_A.setTextColor(unSelectedColor);
				Page_B.setTextColor(unSelectedColor);
				break;
			}
		}
	}

	/**
	 * 定义适配器
	 */
	class myPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragmentList;

		public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
			super(fm);
			this.fragmentList = fragmentList;
		}

		/**
		 * 得到每个页面
		 */
		@Override
		public Fragment getItem(int arg0) {
			return (fragmentList == null || fragmentList.size() == 0) ? null
					: fragmentList.get(arg0);
		}

		/**
		 * 每个页面的title
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}

		/**
		 * 页面的总个数
		 */
		@Override
		public int getCount() {
			return fragmentList == null ? 0 : fragmentList.size();
		}
	}

}