package com.ti;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;
public class VehicleApp extends Application {
	//this is local develop_test
	private static VehicleApp instance;


	private List<Activity> activitys = new LinkedList<Activity>();

	private List<Activity> activityList = new LinkedList<Activity>();

	// 设置登录前的activity
	private Activity activity;

	private boolean openCrashHandler = false;

	public boolean appSwitch = false;

	private boolean isShowAssistant = true;
	private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime()
			.maxMemory();// 分配的可用内存
	public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;// 使用的缓存数量
	// 如果这边报缺少jar包错误，请添加fbcore-v0.5.0.jar fresco-v0.5.0.jar这两个jar包

	public void onCreate() {
		super.onCreate();
		instance = VehicleApp.this;
	}



	public static VehicleApp getInstance() {
		return instance;
	}


	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		if (activitys != null && activitys.size() > 0) {
			if (!activitys.contains(activity)) {
				activitys.add(activity);
			}
		} else {
			activitys.add(activity);
		}

	}

	public List<Activity> getActivitys() {
		return activitys;
	}

	public void exit() {
		if (activitys != null && activitys.size() > 0) {
			for (Activity activity : activitys) {
				activity.finish();
			}
		}
		System.exit(0);

		// 直接在服务中，暂停了。。。

		// 以便下次打开下载管理时，可以置成下载状态中。。。。
		// 发送广播，让其全部暂停掉下载...
		/*
		 * sendBroadcast(new
		 * Intent(DownloadService.PAUSEALLRECEIVER_ACTION).putExtra("isExit",
		 * true));
		 */
		// Log.i(TAG, "发送广播方式退出了.....");
		// 怎么停止掉服务...

		// Process.killProcess(Process.myPid());//停止掉本应用进程，即退出了应用
	}

	public void finishAll() {
		if (activitys != null && activitys.size() > 0) {
			for (Activity activity : activitys) {
				activity.finish();
			}
		}
	}


	// 添加Activity到容器中
	public void addActivityToList(Activity activity) {
		if (activityList != null && activityList.size() > 0) {
			if (!activityList.contains(activity)) {
				activityList.add(activity);
			}
		} else {
			activityList.add(activity);
		}

	}

	public List<Activity> getActivityLists() {
		return activityList;
	}

	public void finishAllActivityLists() {
		if (activityList != null && activityList.size() > 0) {
			for (Activity activity : activityList) {
				activity.finish();
			}
		}
	}


	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public boolean isAppSwitch() {
		return appSwitch;
	}

	public void setAppSwitch(boolean appSwitch) {
		this.appSwitch = appSwitch;
	}

	public void setShowAssistant(boolean isShowAssistant) {
		this.isShowAssistant = isShowAssistant;
	}

	public boolean isShowAssistant() {
		return isShowAssistant;
	}

}
