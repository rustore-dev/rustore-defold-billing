-- To get access to the functions, you need to put:
-- require "extension_rustore_billing.scripts.RuStoreBillingClient"
-- in any script using the functions.

local RuStoreBillingClient = {}

_rustore_billing_client_instance = nil

function RuStoreBillingClient:new()
	local obj = {}
	setmetatable(obj, self)
	self.__index = self
	return obj
end


function RuStoreBillingClient:get_instance()
	if not _rustore_billing_client_instance then
		_rustore_billing_client_instance = self:new()
	end
	return _rustore_billing_client_instance
end


function RuStoreBillingClient:connect(channel, callback)
	rustorebilling.connect(channel, callback)
end


function RuStoreBillingClient:init(app_id, scheme)
	rustorebilling.init(app_id, scheme)
end


function RuStoreBillingClient:check_purchases_availability()
	rustorebilling.check_purchases_availability()
end


function RuStoreBillingClient:get_products(product_ids)
	rustorebilling.get_products(product_ids)
end


function RuStoreBillingClient:purchase_product(product_id)
	rustorebilling.purchase_product(product_id)
end


function RuStoreBillingClient:get_purchases()
	rustorebilling.get_purchases()
end


function RuStoreBillingClient:confirm_purchase(product_id)
	rustorebilling.confirm_purchase(product_id)
end


function RuStoreBillingClient:delete_purchase(product_id)
	rustorebilling.delete_purchase(product_id)
end


function RuStoreBillingClient:get_purchase_info(product_id)
	rustorebilling.get_purchase_info(product_id)
end


function RuStoreBillingClient:set_theme(product_id)
	rustorebilling.set_theme(product_id)
end

return RuStoreBillingClient
