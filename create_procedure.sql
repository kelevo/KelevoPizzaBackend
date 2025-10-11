DROP PROCEDURE IF EXISTS take_random_pizza_order;
DELIMITER $$

CREATE PROCEDURE take_random_pizza_order (
    IN p_id_customer VARCHAR(15),
    IN p_method CHAR(1),
    OUT p_order_taken BOOLEAN
)
main_block: BEGIN
    DECLARE v_id_random_pizza INT;
    DECLARE v_price DECIMAL(10,2);
    DECLARE v_new_order_id INT;
    DECLARE v_new_item_id INT;

    SET p_order_taken = FALSE;

    -- Buscar una pizza aleatoria disponible
    SELECT id_pizza, price
    INTO v_id_random_pizza, v_price
    FROM pizza
    WHERE available = TRUE
    ORDER BY RAND()
    LIMIT 1;

    -- Si no se encontró ninguna pizza disponible, salir
    IF v_id_random_pizza IS NULL THEN
        LEAVE main_block;
    END IF;

    -- Crear orden
    INSERT INTO pizza_order (id_customer, date, method, total)
    VALUES (p_id_customer, NOW(), p_method, v_price);

    SET v_new_order_id = LAST_INSERT_ID();

    -- Calcular el siguiente id_item para esa orden
    SELECT IFNULL(MAX(id_item), 0) + 1 INTO v_new_item_id
    FROM order_item
    WHERE id_order = v_new_order_id;

    -- Insertar detalle de orden
    INSERT INTO order_item (id_item, id_order, id_pizza, quantity, price)
    VALUES (v_new_item_id, v_new_order_id, v_id_random_pizza, 1, v_price);

    SET p_order_taken = TRUE;

END main_block$$
DELIMITER ;