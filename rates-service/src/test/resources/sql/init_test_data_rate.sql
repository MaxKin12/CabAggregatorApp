insert into rates (id, ride_id, passenger_id, driver_id, recipient, value, comment)
values (1, 1, (UNHEX(REPLACE('04f13490-2048-4b99-8514-17a4e90dc3ba', '-', ''))), (UNHEX(REPLACE('4ba41155-bcff-48d5-8675-bc0aac800e99', '-', ''))), 1, 5, 'So cool driver!');

insert into rates (id, ride_id, passenger_id, driver_id, recipient, value, comment)
values (2, 1, (UNHEX(REPLACE('04f13490-2048-4b99-8514-17a4e90dc3ba', '-', ''))), (UNHEX(REPLACE('4ba41155-bcff-48d5-8675-bc0aac800e99', '-', ''))), 1, 0, 'Rude passenger...');
