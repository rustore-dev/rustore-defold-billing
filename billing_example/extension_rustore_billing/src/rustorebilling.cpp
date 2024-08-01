#define EXTENSION_NAME RuStoreBilling
#define LIB_NAME "RuStoreBilling"
#define MODULE_NAME "rustorebilling"

#if defined(DM_PLATFORM_ANDROID)

#include <dmsdk/sdk.h>
#include <dmsdk/dlib/android.h>
#include <string>
#include <vector>

static int Init(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.billing.RuStoreBilling");
    jmethodID method = env->GetStaticMethodID(cls, "init", "(Landroid/app/Activity;Ljava/lang/String;Ljava/lang/String;Z)V");

    const char* id = (char*)luaL_checkstring(L, 1);
    const char* scheme = (char*)luaL_checkstring(L, 2);

    jstring jid = env->NewStringUTF(id);
    jstring jscheme = env->NewStringUTF(scheme);
    jboolean jdebugLogs = false;

    int n = lua_gettop(L);
    if (n > 2) jdebugLogs = (jboolean)lua_toboolean(L, 3);

    env->CallStaticVoidMethod(cls, method, dmGraphics::GetNativeAndroidActivity(), jid, jscheme, jdebugLogs);

    env->DeleteLocalRef(jid);
    env->DeleteLocalRef(jscheme);

    thread.Detach();
    
    return 0;
}

static int CheckPurchasesAvailability(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.billing.RuStoreBilling");
    jmethodID method = env->GetStaticMethodID(cls, "checkPurchasesAvailability", "()V");

    env->CallStaticVoidMethod(cls, method);

    thread.Detach();
    
    return 0;
}

static int GetProducts(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    std::vector<std::string> productIds;
    if (lua_istable(L, 1))
    {
        int tableSize = lua_objlen(L, 1);
        for (int i = 1; i <= tableSize; i++)
        {
            lua_rawgeti(L, 1, i);
            if (lua_isstring(L, -1))
            {
                const char* productId = lua_tostring(L, -1);
                productIds.push_back(productId);
            }
            lua_pop(L, 1);
        }
    }

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.billing.RuStoreBilling");
    jmethodID method = env->GetStaticMethodID(cls, "getProducts", "([Ljava/lang/String;)V");

    jobjectArray jproductIds = env->NewObjectArray(productIds.size(), env->FindClass("java/lang/String"), nullptr);
    for (int i = 0; i < productIds.size(); i++) {
        jstring jproductId = env->NewStringUTF(productIds[i].c_str());
        env->SetObjectArrayElement(jproductIds, i, jproductId);
        env->DeleteLocalRef(jproductId);
    }
    
    env->CallStaticVoidMethod(cls, method, jproductIds);

    env->DeleteLocalRef(jproductIds);
    
    thread.Detach();
    
    return 0;
}

static int PurchaseProduct(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.billing.RuStoreBilling");
    jmethodID method = env->GetStaticMethodID(cls, "purchaseProduct", "(Ljava/lang/String;Ljava/lang/String;)V");

    const char* productId = (char*)luaL_checkstring(L, 1);
    jstring jproductId = env->NewStringUTF(productId);

    jstring jparams;
    
    int n = lua_gettop(L);
    if (n > 1) {
        const char* params = (char*)luaL_checkstring(L, 2);
        jparams = env->NewStringUTF(params);
    } else {
        jparams = env->NewStringUTF("");
    }

    env->CallStaticVoidMethod(cls, method, jproductId, jparams);

    env->DeleteLocalRef(jproductId);
    env->DeleteLocalRef(jparams);
    
    thread.Detach();

    return 0;
}

static int GetPurchases(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.billing.RuStoreBilling");
    jmethodID method = env->GetStaticMethodID(cls, "getPurchases", "()V");

    env->CallStaticVoidMethod(cls, method);

    thread.Detach();

    return 0;
}

static int ConfirmPurchase(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.billing.RuStoreBilling");
    jmethodID method = env->GetStaticMethodID(cls, "confirmPurchase", "(Ljava/lang/String;)V");

    const char* productId = (char*)luaL_checkstring(L, 1);
    jstring jproductId = env->NewStringUTF(productId);

    env->CallStaticVoidMethod(cls, method, jproductId);

    env->DeleteLocalRef(jproductId);
    
    thread.Detach();

    return 0;
}

static int DeletePurchase(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.billing.RuStoreBilling");
    jmethodID method = env->GetStaticMethodID(cls, "deletePurchase", "(Ljava/lang/String;)V");

    const char* productId = (char*)luaL_checkstring(L, 1);
    jstring jproductId = env->NewStringUTF(productId);

    env->CallStaticVoidMethod(cls, method, jproductId);

    env->DeleteLocalRef(jproductId);
    
    thread.Detach();

    return 0;
}

static int GetPurchaseInfo(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.billing.RuStoreBilling");
    jmethodID method = env->GetStaticMethodID(cls, "getPurchaseInfo", "(Ljava/lang/String;)V");

    const char* productId = (char*)luaL_checkstring(L, 1);
    jstring jproductId = env->NewStringUTF(productId);

    env->CallStaticVoidMethod(cls, method, jproductId);

    env->DeleteLocalRef(jproductId);
    
    thread.Detach();

    return 0;
}

static int SetTheme(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.billing.RuStoreBilling");
    jmethodID method = env->GetStaticMethodID(cls, "setTheme", "(I)V");

    jint jthemeCode = static_cast<jint>luaL_checkint(L, 1);

    env->CallStaticVoidMethod(cls, method, jthemeCode);

    thread.Detach();

    return 0;
}

static int SetErrorHandling(lua_State* L)
{
    DM_LUA_STACK_CHECK(L, 0);

    dmAndroid::ThreadAttacher thread;
    JNIEnv* env = thread.GetEnv();

    jclass cls = dmAndroid::LoadClass(env, "ru.rustore.defold.billing.RuStoreBilling");
    jmethodID method = env->GetStaticMethodID(cls, "setErrorHandling", "(Z)V");

    jboolean jvalue = (jboolean)lua_toboolean(L, 1);

    env->CallStaticVoidMethod(cls, method, jvalue);

    thread.Detach();

    return 0;
}

// Functions exposed to Lua
static const luaL_reg Module_methods[] =
{
    {"init", Init},
    {"check_purchases_availability", CheckPurchasesAvailability},
    {"get_products", GetProducts},
    {"purchase_product", PurchaseProduct},
    {"get_purchases", GetPurchases},
    {"confirm_purchase", ConfirmPurchase},
    {"delete_purchase", DeletePurchase},
    {"get_purchase_info", GetPurchaseInfo},
    {"set_theme", SetTheme},
    {"set_error_handling", SetErrorHandling},
    {0, 0}
};

#else

static const luaL_reg Module_methods[] =
{
    {0, 0}
};

#endif

static void LuaInit(lua_State* L)
{
    int top = lua_gettop(L);
    
    luaL_register(L, MODULE_NAME, Module_methods);

    lua_pop(L, 1);
    assert(top == lua_gettop(L));
}

static dmExtension::Result AppInitializeMyExtension(dmExtension::AppParams* params)
{
    dmLogInfo("AppInitializeMyExtension");
    return dmExtension::RESULT_OK;
}

static dmExtension::Result InitializeMyExtension(dmExtension::Params* params)
{
    LuaInit(params->m_L);
    dmLogInfo("Registered %s Extension", MODULE_NAME);
    return dmExtension::RESULT_OK;
}

static dmExtension::Result AppFinalizeMyExtension(dmExtension::AppParams* params)
{
    dmLogInfo("AppFinalizeMyExtension");
    return dmExtension::RESULT_OK;
}

static dmExtension::Result FinalizeMyExtension(dmExtension::Params* params)
{
    dmLogInfo("FinalizeMyExtension");
    return dmExtension::RESULT_OK;
}

static dmExtension::Result OnUpdateMyExtension(dmExtension::Params* params)
{
    dmLogInfo("OnUpdateMyExtension");
    return dmExtension::RESULT_OK;
}

static void OnEventMyExtension(dmExtension::Params* params, const dmExtension::Event* event)
{
    switch(event->m_Event)
    {
        case dmExtension::EVENT_ID_ACTIVATEAPP:
        dmLogInfo("OnEventMyExtension - EVENT_ID_ACTIVATEAPP");
        break;
        case dmExtension::EVENT_ID_DEACTIVATEAPP:
        dmLogInfo("OnEventMyExtension - EVENT_ID_DEACTIVATEAPP");
        break;
        case dmExtension::EVENT_ID_ICONIFYAPP:
        dmLogInfo("OnEventMyExtension - EVENT_ID_ICONIFYAPP");
        break;
        case dmExtension::EVENT_ID_DEICONIFYAPP:
        dmLogInfo("OnEventMyExtension - EVENT_ID_DEICONIFYAPP");
        break;
        default:
        dmLogWarning("OnEventMyExtension - Unknown event id");
        break;
    }
}

// Defold SDK uses a macro for setting up extension entry points:
//
// DM_DECLARE_EXTENSION(symbol, name, app_init, app_final, init, update, on_event, final)

// MyExtension is the C++ symbol that holds all relevant extension data.
// It must match the name field in the `ext.manifest`
DM_DECLARE_EXTENSION(EXTENSION_NAME, LIB_NAME, AppInitializeMyExtension, AppFinalizeMyExtension, InitializeMyExtension, OnUpdateMyExtension, OnEventMyExtension, FinalizeMyExtension)
