SET @taken = FALSE;
CALL take_random_pizza_order('262132898', 'D', @taken);
SELECT @taken AS order_taken;