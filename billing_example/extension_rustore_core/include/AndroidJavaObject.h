#pragma once

#include <dmsdk/sdk.h>
#include <dmsdk/dlib/android.h>

namespace RuStoreSDK
{
	class AndroidJavaObject
	{
		public:
		jclass cls;
		jobject obj;

		AndroidJavaObject()
		{
			cls = nullptr;
			obj = nullptr;
		}
	};
}
