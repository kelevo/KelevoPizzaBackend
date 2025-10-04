DROP PROCEDURE IF EXISTS `take_random_pizza_order`;
DELIMITER $$
CREATE PROCEDURE `take_random_pizza_order`(
    IN id_customer VARCHAR(15),
    IN method CHAR(1),
    OUT order_taken TINYINT(1)  -- mejor usar TINYINT en lugar de BOOL
)
BEGIN
    DECLARE id_random_pizza INT;
    DECLARE price_random_pizza DECIMAL(5,2);
    DECLARE price_with_discount DECIMAL(5,2);
    DECLARE WITH_ERRORS TINYINT(1) DEFAULT 0;  -- BOOL es alias, pero a veces da bronca en SP

    -- el HANDLER también debe ir justo después de los DECLARE
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    BEGIN
        SET WITH_ERRORS = 1;
    END;

    -- aquí ya puedes empezar con las queries
    SELECT id_pizza, price
    INTO id_random_pizza, price_random_pizza
    FROM pizza
    WHERE available = 1
    ORDER BY RAND()
    LIMIT 1;
    
    IF id_random_pizza IS NULL THEN
        SET order_taken = 0;
        SELECT order_taken;
        RETURN;
    END IF;
    
    SET price_with_discount = price_random_pizza - (price_random_pizza * 0.20);
    
    START TRANSACTION;
    
    INSERT INTO pizza_order (id_customer, `date`, total, method, additional_notes)
    VALUES (id_customer, SYSDATE(), price_with_discount, method, '20% OFF PIZZA RANDOM PROMOTION');
    
    INSERT INTO order_item (id_order, id_pizza, quantity, price)
    VALUES (LAST_INSERT_ID(), id_random_pizza, 1, price_random_pizza);
    
    IF WITH_ERRORS = 1 THEN 
        SET order_taken = 0;
        ROLLBACK;
    ELSE 
        SET order_taken = 1;
        COMMIT;
    END IF;
    
    SELECT order_taken;
END$$
DELIMITER ;