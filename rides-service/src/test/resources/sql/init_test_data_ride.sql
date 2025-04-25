insert into rides (id, passenger_id, driver_id, car_id, pick_up_address, destination_address, status, order_time, price)
values (1, (UNHEX(REPLACE('04f13490-2048-4b99-8514-17a4e90dc3ba', '-', ''))), (UNHEX(REPLACE('4ba41155-bcff-48d5-8675-bc0aac800e99', '-', ''))), 1, 'Независимости 4, Минск', 'Гикало 9, Минск', 500, '2025-02-02 15:00:00', 17.50);

insert into rides (id, passenger_id, driver_id, car_id, pick_up_address, destination_address, status, order_time, price)
values (2, (UNHEX(REPLACE('2519b3b3-daa2-49e7-850a-908fee962387', '-', ''))), (UNHEX(REPLACE('05a91c2b-e1d6-41a5-83ed-ee4711925924', '-', ''))), 2, 'Притыцкого 132, Барановичи', 'Новаторов 120, Барановичи', 600, '2025-02-02 16:00:00', 28);
