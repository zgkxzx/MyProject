#include <fcntl.h>
#include<string.h>
#include<jni.h>
#include"com_example_myndkdemo_MainActivity.h"
#include"android/log.h"


static const char *TAG="ndklibs";

#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,  TAG,fmt,##args)
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, TAG,fmt,##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, TAG,fmt,##args)

JNIEXPORT jint JNICALL Java_com_example_myndkdemo_MainActivity_add
  (JNIEnv *env, jobject thiz, jint param1, jint param2)
{
	int i;
	LOGI("Java_com_example_myndkdemo_MainActivity--add");
	for(i=0;i<param1;i++)
	{
		param2+=i;

	}
	return param2;
}

JNIEXPORT jint JNICALL Java_com_example_myndkdemo_MainActivity_mice
  (JNIEnv *env, jobject	thiz, jint param1, jint param2)
{
	LOGI("Java_com_example_myndkdemo_MainActivity--mice");

}

JNIEXPORT jint JNICALL Java_com_example_myndkdemo_MainActivity_aaaa
  (JNIEnv *env, jobject thiz, jint value)
{

	LOGI("Java_com_example_myndkdemo_MainActivity--aaaa function");
	LOGI("the value is:%d",value);
}
JNIEXPORT jint JNICALL Java_com_example_myndkdemo_MainActivity_bbbb
  (JNIEnv *env, jobject obj)
{
	LOGI("Java_com_example_myndkdemo_MainActivity--bbbb function");

	//get Object Class
	jclass cls = (*env)->GetObjectClass(env,obj);
	//get class method
	jmethodID mid =  (*env)->GetMethodID(env,cls,"callback","()V");

	if(mid == NULL)
	{
		return;
	}
	LOGI("In C\n");
	// call the method
	(* env)->CallVoidMethod(env,obj,mid);

}

JNIEXPORT jint JNICALL Java_com_example_myndkdemo_MainActivity_accessField
  (JNIEnv *env, jobject obj)
{
	jfieldID fid;
	jstring jstr;
	const char *str;

	LOGI("Java_com_example_myndkdemo_MainActivity_accessField");
	//get object class
	jclass cls = (*env)->GetObjectClass(env,obj);

	LOGI("In C:\n");
	// get class field
	fid = (*env)->GetFieldID(env,cls,"s","Ljava/lang/String;");

	if(fid==NULL)
		return;
	//get filed value
	jstr = (*env)->GetObjectField(env,obj,fid);

	// jstr convert to str : jni type to c type
	str = (*env)->GetStringUTFChars(env,jstr,NULL);
	if(str == NULL)
		return;
	LOGI("s = %s\n",str);
	//release the jstr memory
	(*env)->ReleaseStringUTFChars(env,jstr,str);
	//init jstr whose name is zgkxzx
	jstr = (*env)->NewStringUTF(env,"zgkxzx");

	if(jstr == NULL)
		return;
	//set object filed value
	(*env)->SetObjectField(env,obj,fid,jstr);
}
