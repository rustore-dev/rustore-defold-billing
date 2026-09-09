-- Stub for Defold Editor (autocomplete + diagnostics).
-- Runtime module is provided by native extension via luaL_register(MODULE_NAME, Module_methods).
-- Do not require this file.
--
-- Enums are available in global: RuStoreBillingEnums (registered by native extension).

---@class rustorebilling
local M = {}

--- Инициализация Billing.
--- @param id string
--- @param scheme string
--- @param debug_logs boolean|nil
function M.init(id, scheme, debug_logs) end

--- (DEPRECATED) Проверить доступность покупок.
--- Работает только для флоу с авторизованным пользователем в RuStore.
function M.check_purchases_availability() end

--- Запросить статус авторизации пользователя.
function M.get_authorization_status() end

--- Запросить продукты по списку product_id.
--- @param product_ids string[]
function M.get_products(product_ids) end

--- Купить продукт.
--- @param product_id string
--- @param params string|nil @JSON-строка параметров (если не передать, будет "")
function M.purchase_product(product_id, params) end

--- Запросить список покупок.
function M.get_purchases() end

--- Подтвердить покупку.
--- @param product_id string
function M.confirm_purchase(product_id) end

--- Удалить покупку.
--- @param product_id string
function M.delete_purchase(product_id) end

--- Запросить информацию о покупке.
--- @param product_id string
function M.get_purchase_info(product_id) end

--- Установить тему платежного интерфейса.
--- @param theme_code integer @например RuStoreBillingEnums.Theme.DARK
function M.set_theme(theme_code) end

---@type rustorebilling
rustorebilling = rustorebilling
