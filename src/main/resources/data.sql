INSERT INTO travel_package (id, name, passenger_capacity) VALUES (1, 'package 1', 3);
INSERT INTO travel_package (id, name, passenger_capacity) VALUES (2, 'package 2', 5);
INSERT INTO travel_package (id, name, passenger_capacity) VALUES (3, 'package 3', 7);

INSERT INTO itinerary (id, name) VALUES (1, 'itinerary 1');
INSERT INTO itinerary (id, name) VALUES (2, 'itinerary 2');
INSERT INTO itinerary (id, name) VALUES (3, 'itinerary 3');
INSERT INTO itinerary (id, name) VALUES (4, 'itinerary 4');
INSERT INTO itinerary (id, name) VALUES (5, 'itinerary 5');

INSERT INTO travel_package_itinerary_map (travel_package_id, itinerary_id) VALUES (1, 1);
INSERT INTO travel_package_itinerary_map (travel_package_id, itinerary_id) VALUES (1, 2);
INSERT INTO travel_package_itinerary_map (travel_package_id, itinerary_id) VALUES (1, 3);
INSERT INTO travel_package_itinerary_map (travel_package_id, itinerary_id) VALUES (2, 1);
INSERT INTO travel_package_itinerary_map (travel_package_id, itinerary_id) VALUES (2, 3);
INSERT INTO travel_package_itinerary_map (travel_package_id, itinerary_id) VALUES (2, 5);
INSERT INTO travel_package_itinerary_map (travel_package_id, itinerary_id) VALUES (3, 2);
INSERT INTO travel_package_itinerary_map (travel_package_id, itinerary_id) VALUES (3, 4);

INSERT INTO activity (id, name, description, itinerary_id, cost, capacity) VALUES (1, 'activity 1', 'Activity 1', 1, 10, 3);
INSERT INTO activity (id, name, description, itinerary_id, cost, capacity) VALUES (2, 'activity 2', 'Activity 2', 1, 20, 3);
INSERT INTO activity (id, name, description, itinerary_id, cost, capacity) VALUES (3, 'activity 3', 'Activity 3', 2, 30, 3);
INSERT INTO activity (id, name, description, itinerary_id, cost, capacity) VALUES (4, 'activity 4', 'Activity 4', 2, 10, 3);
INSERT INTO activity (id, name, description, itinerary_id, cost, capacity) VALUES (5, 'activity 5', 'Activity 5', 3, 10, 2);
INSERT INTO activity (id, name, description, itinerary_id, cost, capacity) VALUES (6, 'activity 6', 'Activity 6', 4, 20, 1);
INSERT INTO activity (id, name, description, itinerary_id, cost, capacity) VALUES (7, 'activity 7', 'Activity 7', 5, 10, 2);

INSERT INTO passenger (passenger_number, name, membership, balance) VALUES (1, 'Passenger 1', 'STANDARD', 50);
INSERT INTO passenger (passenger_number, name, membership, balance) VALUES (2, 'Passenger 2', 'STANDARD', 30);
INSERT INTO passenger (passenger_number, name, membership, balance) VALUES (3, 'Passenger 3', 'GOLD', 40);
INSERT INTO passenger (passenger_number, name, membership, balance) VALUES (4, 'Passenger 4', 'PREMIUM', 0);
