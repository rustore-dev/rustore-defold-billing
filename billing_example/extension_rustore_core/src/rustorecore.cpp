#define EXTENSION_NAME RuStoreCore
#define LIB_NAME "RuStoreCore"
#define MODULE_NAME "rustorecore"

#if defined(DM_PLATFORM_ANDROID)

#include "RuStoreChannelListener.h"
#include "ChannelCallbackManager.h"

using namespace RuStoreSDK;

static void InitRuStoreCallbacks()
{
    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();
    
    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.core.RuStoreCore");
    jmethodID method = env->GetStaticMethodID(cls, "setChannelListener", "(Lru/rustore/defold/core/callbacks/IRuStoreChannelListener;)V");
    jobject wrapper = RuStoreChannelListener::Instance()->GetJWrapper();
    
    env->CallStaticVoidMethod(cls, method, wrapper);

    thread.Detach();
}

static int Connect(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    const char* channel = (char*)luaL_checkstring(L, 1);
    dmScript::LuaCallbackInfo* callback = dmScript::CreateCallback(L, 2);

    auto channelCallbackItem = std::make_shared<ChannelCallbackItem>(channel, callback);
    ChannelCallbackManager::Instance()->AddChannelCallback(channelCallbackItem);

    return 0;
}

static int ShowToast(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();
    
    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.core.RuStoreCore");
    jmethodID method = env->GetStaticMethodID(cls, "showToast", "(Landroid/app/Activity;Ljava/lang/String;)V");

    const char* msg = (char*)luaL_checkstring(L, 1);
    jstring jmsg = env->NewStringUTF(msg);

    env->CallStaticVoidMethod(cls, method, dmGraphics::GetNativeAndroidActivity(), jmsg);

    thread.Detach();

    return 0;
}

static int LogVerbose(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.core.RuStoreCore");
    jmethodID method = env->GetStaticMethodID(cls, "logVerbose", "(Ljava/lang/String;Ljava/lang/String;)V");

    const char* tag = (char*)luaL_checkstring(L, 1);
    const char* msg = (char*)luaL_checkstring(L, 2);

    jstring jtag = env->NewStringUTF(tag);
    jstring jmsg = env->NewStringUTF(msg);

    env->CallStaticVoidMethod(cls, method, jtag, jmsg);

    env->DeleteLocalRef(jtag);
    env->DeleteLocalRef(jmsg);

    thread.Detach();

    return 0;
}

static int LogDebug(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.core.RuStoreCore");
    jmethodID method = env->GetStaticMethodID(cls, "logDebug", "(Ljava/lang/String;Ljava/lang/String;)V");

    const char* tag = (char*)luaL_checkstring(L, 1);
    const char* msg = (char*)luaL_checkstring(L, 2);

    jstring jtag = env->NewStringUTF(tag);
    jstring jmsg = env->NewStringUTF(msg);

    env->CallStaticVoidMethod(cls, method, jtag, jmsg);

    env->DeleteLocalRef(jtag);
    env->DeleteLocalRef(jmsg);

    thread.Detach();

    return 0;
}

static int LogInfo(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.core.RuStoreCore");
    jmethodID method = env->GetStaticMethodID(cls, "logInfo", "(Ljava/lang/String;Ljava/lang/String;)V");

    const char* tag = (char*)luaL_checkstring(L, 1);
    const char* msg = (char*)luaL_checkstring(L, 2);

    jstring jtag = env->NewStringUTF(tag);
    jstring jmsg = env->NewStringUTF(msg);

    env->CallStaticVoidMethod(cls, method, jtag, jmsg);

    env->DeleteLocalRef(jtag);
    env->DeleteLocalRef(jmsg);

    thread.Detach();

    return 0;
}

static int LogWarning(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();
    
    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.core.RuStoreCore");
    jmethodID method = env->GetStaticMethodID(cls, "logWarning", "(Ljava/lang/String;Ljava/lang/String;)V");

    const char* tag = (char*)luaL_checkstring(L, 1);
    const char* msg = (char*)luaL_checkstring(L, 2);

    jstring jtag = env->NewStringUTF(tag);
    jstring jmsg = env->NewStringUTF(msg);

    env->CallStaticVoidMethod(cls, method, jtag, jmsg);

    env->DeleteLocalRef(jtag);
    env->DeleteLocalRef(jmsg);
    
    thread.Detach();
    
    return 0;
}

static int LogError(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.core.RuStoreCore");
    jmethodID method = env->GetStaticMethodID(cls, "logError", "(Ljava/lang/String;Ljava/lang/String;)V");

    const char* tag = (char*)luaL_checkstring(L, 1);
    const char* msg = (char*)luaL_checkstring(L, 2);

    jstring jtag = env->NewStringUTF(tag);
    jstring jmsg = env->NewStringUTF(msg);

    env->CallStaticVoidMethod(cls, method, jtag, jmsg);

    env->DeleteLocalRef(jtag);
    env->DeleteLocalRef(jmsg);

    thread.Detach();

    return 0;
}

static int CopyToClipboard(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.core.RuStoreCore");
    jmethodID method = env->GetStaticMethodID(cls, "copyToClipboard", "(Landroid/app/Activity;Ljava/lang/String;)V");

    const char* text = (char*)luaL_checkstring(L, 1);
    jstring jtext = env->NewStringUTF(text);

    env->CallStaticVoidMethod(cls, method, dmGraphics::GetNativeAndroidActivity(), jtext);

    env->DeleteLocalRef(jtext);

    thread.Detach();

    return 0;
}

static int GetFromClipboard(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 1);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.core.RuStoreCore");
    jmethodID method = env->GetStaticMethodID(cls, "getFromClipboard", "(Landroid/app/Activity;)Ljava/lang/String;");

    jstring jtext = (jstring)env->CallStaticObjectMethod(cls, method, dmGraphics::GetNativeAndroidActivity());

    const char* ctext = env->GetStringUTFChars(jtext, nullptr);
    lua_pushstring(L, ctext);
    env->ReleaseStringUTFChars(jtext, ctext);
    
    thread.Detach();
    
    return 1;
}

static int GetStringResources(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 1);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    const char* name = (char*)luaL_checkstring(L, 1);
    jstring jname = env->NewStringUTF(name);

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.core.RuStoreCore");
    jmethodID method = env->GetStaticMethodID(cls, "getStringResources", "(Landroid/app/Activity;Ljava/lang/String;)Ljava/lang/String;");

    jstring jtext = (jstring)env->CallStaticObjectMethod(cls, method, dmGraphics::GetNativeAndroidActivity(), jname);

    const char* ctext = env->GetStringUTFChars(jtext, nullptr);
    lua_pushstring(L, ctext);
    env->ReleaseStringUTFChars(jtext, ctext);
    
    env->DeleteLocalRef(jname);

    thread.Detach();
    
    return 1;
}

#endif

static const luaL_reg Module_methods[] =
{
    {"connect", Connect},
    {"log_verbose", LogVerbose},
    {"log_debug", LogDebug},
    {"log_info", LogInfo},
    {"log_warning", LogWarning},
    {"log_error", LogError},
    {"show_toast", ShowToast},
    {"copy_to_clipboard", CopyToClipboard},
    {"get_from_clipboard", GetFromClipboard},
    {"get_string_resources", GetStringResources},
    {0, 0}
};

static void LuaInit(lua_State* L)
{
    int top = lua_gettop(L);
    luaL_register(L, MODULE_NAME, Module_methods);
    lua_pop(L, 1);
    assert(top == lua_gettop(L));
}

static dmExtension::Result AppInitializeMyExtension(dmExtension::AppParams* params)
{
    return dmExtension::RESULT_OK;
}

static dmExtension::Result InitializeMyExtension(dmExtension::Params* params)
{
    LuaInit(params->m_L);
    InitRuStoreCallbacks();
    
    return dmExtension::RESULT_OK;
}

static dmExtension::Result AppFinalizeMyExtension(dmExtension::AppParams* params)
{
    return dmExtension::RESULT_OK;
}

static dmExtension::Result UpdateMyExtension(dmExtension::Params* params)
{
    auto queue = QueueCallbackManager::Instance()->GetExexuteQueueCallback();
    
    while (!queue.empty())
    {
        auto item = queue.front();
        queue.pop();

        const char* channel = item->channel.c_str();
        const char* value = item->value.c_str();

        auto _callbacks = ChannelCallbackManager::Instance()->FindLuaCallbacksByChannel(channel);
        for (const auto& callback : _callbacks)
        {
            lua_State* L = dmScript::GetCallbackLuaContext(callback);

            DM_LUA_STACK_CHECK(L, 0);

            if (!dmScript::SetupCallback(callback)) continue;

            lua_pushstring(L, channel);
            lua_pushstring(L, value);

            dmScript::PCall(L, 3, 0); // self + # user arguments

            dmScript::TeardownCallback(callback);
        }
    }

    return dmExtension::RESULT_OK;
}

DM_DECLARE_EXTENSION(EXTENSION_NAME, LIB_NAME, AppInitializeMyExtension, AppFinalizeMyExtension, InitializeMyExtension, UpdateMyExtension, nullptr, nullptr)
