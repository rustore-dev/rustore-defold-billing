#include "billing_enums.h"
#include "billing_enums_defs.h"

static int ReadonlyNewIndex(lua_State* L)
{
	return luaL_error(L, "Attempt to modify readonly enum table");
}

static void MakeReadonly(lua_State* L, int index)
{
	if (index < 0) index = lua_gettop(L) + index + 1;

	lua_newtable(L);                         // mt
	lua_pushcfunction(L, ReadonlyNewIndex);  // mt.__newindex
	lua_setfield(L, -2, "__newindex");

	lua_pushboolean(L, 0);                   // mt.__metatable = false
	lua_setfield(L, -2, "__metatable");

	lua_setmetatable(L, index);
}

static inline void PushIntField(lua_State* L, const char* key, int value)
{
	lua_pushinteger(L, (lua_Integer)value);
	lua_setfield(L, -2, key);
}

static void PushEnumTable(lua_State* L, void (*fill)(lua_State*))
{
	lua_newtable(L);
	fill(L);
	MakeReadonly(L, -1);
}

static void FillProductStatus(lua_State* L)
{
	#define X(KEY, VAL) PushIntField(L, #KEY, (int)(VAL));
	BILLING_ENUM_PRODUCT_STATUS(X)
	#undef X
}

static void FillProductType(lua_State* L)
{
	#define X(KEY, VAL) PushIntField(L, #KEY, (int)(VAL));
	BILLING_ENUM_PRODUCT_TYPE(X)
	#undef X
}

static void FillPurchaseState(lua_State* L)
{
	#define X(KEY, VAL) PushIntField(L, #KEY, (int)(VAL));
	BILLING_ENUM_PURCHASE_STATE(X)
	#undef X
}

static void FillTheme(lua_State* L)
{
	#define X(KEY, VAL) PushIntField(L, #KEY, (int)(VAL));
	BILLING_ENUM_THEME(X)
	#undef X
}

void RegisterGlobalRuStoreBillingEnums(lua_State* L)
{
	int top = lua_gettop(L);

	lua_newtable(L); // root

	PushEnumTable(L, FillProductStatus);
	lua_setfield(L, -2, "ProductStatus");

	PushEnumTable(L, FillProductType);
	lua_setfield(L, -2, "ProductType");

	PushEnumTable(L, FillPurchaseState);
	lua_setfield(L, -2, "PurchaseState");

	PushEnumTable(L, FillTheme);
	lua_setfield(L, -2, "Theme");

	MakeReadonly(L, -1);
	lua_setglobal(L, "RuStoreBillingEnums");

	assert(top == lua_gettop(L));
}
