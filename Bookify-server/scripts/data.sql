INSERT INTO users (type, is_active, time, address, city, country, zip_code, blocked, email, first_name, last_name, password, phone, profile_image_id, deleted, hash_token)
VALUES
    ('ADMIN', true,'2023-11-30 12:30:00', 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', '21000', false, 'admin@example.com', 'pera', 'peric', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+3816213421', null, false, ""),
    ('GUEST', true, '2023-11-30 12:30:00', 'Some Street 123', 'Cityville', 'Croatia', '12345', false, 'guest@example.com', 'John', 'Doe', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+1234567890', null, false, ""),
    ('OWNER', true,'2023-11-30 12:30:00', 'Another Road 789', 'Townsville', 'Austria', '67890', false, 'owner@example.com', 'Alice', 'Smith', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+9876543210', null, false, ""),
    ('GUEST', true,'2023-11-30 12:30:00', 'Test Street 42', 'Test City', 'Austria', '54321', false, 'guest4@example.com', 'Admin', 'User', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+1122334455', null, false, ""),
    ('GUEST', true, '2023-11-28 08:00:00', 'Guest Lane 87', 'Guestville', 'Slovakia', '98765', false, 'guest2@example.com', 'Jane', 'Doe', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+9988776655', null, false, ""),
    ('OWNER', true,'2023-11-30 12:30:00', 'Owner Avenue 567', 'Ownertown', 'Slovenia', '45678', false, 'owner2@example.com', 'Bob', 'Johnson', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+6655443322', null, false, ""),
    ('OWNER', true, '2023-11-29 15:45:00', 'Admin Road 321', 'Admin City', 'Hungary', '13579', false, 'owner3@example.com', 'Admin', 'Tester', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+2233445566', null, false, ""),
    ('GUEST', true,'2023-11-30 12:30:00', 'Another Guest Street 99', 'Guestropolis', 'Romania', '11223', false, 'guest3@example.com', 'Sam', 'White', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+7788990011', null, false, ""),
    ('OWNER', true, '2023-11-27 20:20:00', 'Owner Street 876', 'Owner City', 'Bulgaria', '554433', false, 'owner4@example.com', 'Eva', 'Brown', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+7788990011', null, false, ""),
    ('GUEST', true,'2023-11-30 12:30:00', 'Admin Lane 765', 'Adminville', 'Montenego', '332211', false, 'guest5@example.com', 'Chris', 'Miller', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+7788990011', null, false, ""),
    ('SYSADMIN', true,'2023-11-30 12:30:00', 'Admin Lane 765', 'AdminSys', 'Montenego', '332211', false, 'adminsys@example.com', 'Chris', 'Adminko', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+7788990011', null, false, "");



INSERT INTO accommodations (name, description, min_guest, max_guest, cancellation_deadline, status, manual, accommodation_type, price_per, address, city, country, zip_code, deleted)
VALUES
    ('Downtown Loft', 'Experience urban living at its finest in our stylish Downtown Loft. This modern loft offers contemporary design and is centrally located for easy access to city attractions and nightlife.', 2, 4, 2, 'APPROVED', true, 'HOTEL', 'PERSON', 'Øster Voldgade 4', 'Copenhagen', 'Denmark', '1350', false),
    ('Riverside Cabin', 'Escape to nature in our Riverside Cabin. Tucked away by the river, this cozy cabin provides a peaceful retreat with the soothing sounds of nature.', 2, 4, 3, 'APPROVED', true, 'HOTEL', 'PERSON', 'Øster Voldgade 5', 'Copenhagen', 'Denmark', '1350',false),
    ('Tranquil Lakehouse', 'Indulge in serenity at our Tranquil Lakehouse. This picturesque retreat is nestled by a pristine lake, offering a perfect blend of relaxation and natural beauty. With a minimum capacity of 4 guests and a maximum capacity of 8, it is an ideal choice for family vacations or a peaceful getaway with friends.', 4, 8, 1, 'APPROVED', true, 'HOTEL', 'PERSON', 'Øster Voldgade 6', 'Copenhagen', 'Denmark', '1350', false),
    ('Historic Manor', 'Step back in time at our Historic Manor. This grand estate exudes charm and elegance, offering a unique experience for those who appreciate the beauty of the past. With a minimum capacity of 10 guests and a maximum capacity of 15, it is perfect for special occasions, events, or a luxurious retreat.', 3, 5, 2, 'APPROVED', true, 'HOTEL', 'PERSON', 'Øster Voldgade 7', 'Copenhagen', 'Denmark', '1350', false),
    ('Luxury Penthouse Suite', 'Elevate your stay in the city with our Luxury Penthouse Suite. This opulent retreat boasts panoramic city views, exquisite furnishings, and premium amenities. With a minimum capacity of 2 guests and a maximum capacity of 4, it is the epitome of urban sophistication.', 2, 4, 1, 'APPROVED', true, 'HOTEL', 'PERSON', 'Dag Hammarskjölds torg 2', 'Malmö', 'Sweden', '211 18', false),
    ('Secluded Mountain Chalet', 'Discover tranquility in our Secluded Mountain Chalet. Tucked away in the hills, this charming chalet offers a cozy escape surrounded by the beauty of nature. With a minimum capacity of 6 guests and a maximum capacity of 10, it is perfect for family gatherings or a peaceful mountain retreat.', 1, 1, 3, 'APPROVED', true, 'ROOM', 'PERSON', 'Dag Hammarskjölds torg 1', 'Malmö', 'Sweden', '211 18', false),
    ('Beachfront Bungalow', 'Experience the epitome of coastal living in our Beachfront Bungalow. This charming abode is just steps away from the sandy shores, offering a perfect retreat for beach lovers. With a minimum capacity of 2 guests and a maximum capacity of 4, it is an ideal getaway for couples or a small family.', 2, 4, 2, 'APPROVED', true, 'ROOM', 'PERSON', 'Dag Hammarskjölds torg 3', 'Malmö', 'Sweden', '211 18', false),
    ('Urban Oasis Apartment', 'Discover an urban oasis in our stylish apartment. Centrally located, this modern space provides a peaceful retreat in the heart of the city. With a minimum capacity of 1 guest and a maximum capacity of 2, it is perfect for solo travelers or a cozy couple’s getaway.', 1, 2, 1, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Üllői út 4', ' Budapest', 'Hungary', '1091', false),
    ('Charming Vineyard Cottage', 'Escape to the countryside in our Charming Vineyard Cottage. Surrounded by vineyards and rolling hills, this quaint cottage offers a peaceful retreat for wine enthusiasts. With a minimum capacity of 4 guests and a maximum capacity of 6, it is an ideal getaway for a small group or family.', 4, 5, 2, 'APPROVED', true, 'ROOM', 'PERSON', 'Üllői út 5', ' Budapest', 'Hungary', '1091', false),
    ('Serenity Beach House', 'Unwind at our Serenity Beach House, a hidden gem on the coast. This spacious beachfront property offers stunning ocean views, making it an ideal retreat for those seeking relaxation by the sea. With a minimum capacity of 6 guests and a maximum capacity of 10, it is perfect for family vacations or a gathering of friends.', 4, 6, 3, 'CREATED', true, 'ROOM', 'PERSON', 'Üllői út 6', ' Budapest', 'Hungary', '1091', false),
    ('Mountain View Lodge', 'Experience the beauty of nature at our Mountain View Lodge. Surrounded by majestic peaks, this cozy lodge offers a warm and inviting atmosphere. With a minimum capacity of 8 guests and a maximum capacity of 12, it is an excellent choice for group retreats or family gatherings in the mountains.', 2, 2, 4, 'APPROVED', true, 'ROOM', 'PERSON', 'Üllői út 7', ' Budapest', 'Hungary', '1091', false),
    ('Rustic Farmhouse Retreat', 'Escape to the countryside in our Rustic Farmhouse Retreat. This charming farmhouse, surrounded by rolling fields, offers a peaceful escape from the hustle and bustle. With a minimum capacity of 4 guests and a maximum capacity of 8, it is perfect for a family getaway or a quiet retreat with friends.', 4, 6, 4, 'APPROVED', true, 'ROOM', 'PERSON', 'Üllői út 8', ' Budapest', 'Hungary', '1091', false),
    ('Elegant City Townhouse', 'Discover sophistication in our Elegant City Townhouse. Located in a historic district, this beautifully appointed townhouse offers a luxurious urban escape. With a minimum capacity of 4 guests and a maximum capacity of 6, it is perfect for a stylish city retreat with friends or family.', 4, 6, 3, 'APPROVED', true, 'ROOM', 'PERSON', 'Üllői út 9', ' Budapest', 'Hungary', '1091', false),
    ('Oceanfront Paradise Villa', 'Experience the epitome of luxury at our Oceanfront Paradise Villa. This exclusive villa boasts breathtaking ocean views, private amenities, and impeccable design. With a minimum capacity of 8 guests and a maximum capacity of 12, it is an ideal choice for a lavish seaside retreat or special occasions.', 3, 4, 2, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Üllői út 10', ' Budapest', 'Hungary', '1091', false),
    ('Enchanted Forest Cabin', 'Immerse yourself in nature at our Enchanted Forest Cabin. Surrounded by ancient trees and wildlife, this cozy cabin offers a magical escape from the everyday. With a minimum capacity of 2 guests and a maximum capacity of 4, it is perfect for a romantic getaway or a peaceful retreat for nature lovers.', 2, 4, 2, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Dag Hammarskjölds torg 10', 'Malmö', 'Sweden', '211 18', false),
    ('Skyline View Penthouse', 'Indulge in luxury with our Skyline View Penthouse. Perched high above the city, this penthouse offers stunning panoramic views, modern amenities, and a sophisticated ambiance. With a minimum capacity of 4 guests and a maximum capacity of 6, it is perfect for a stylish urban getaway with friends or family.', 4, 6, 1, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Dag Hammarskjölds torg 13', 'Malmö', 'Sweden', '211 18', false),
    ('Coastal Clifftop Cottage', 'Perched on a coastal cliff, our Clifftop Cottage offers unrivaled views of the ocean. This charming cottage provides a tranquil and romantic escape, ideal for couples seeking a secluded getaway. With a minimum capacity of 2 guests and a maximum capacity of 3, it is a perfect retreat for those desiring privacy and breathtaking scenery.', 2, 3, 1, 'APPROVED', true, 'ROOM', 'ROOM', 'Dag Hammarskjölds torg 12', 'Malmö', 'Sweden', '211 18', false),
    ('Oceanfront Paradise Villa', 'Experience the epitome of luxury at our Oceanfront Paradise Villa. This exclusive villa boasts breathtaking ocean views, private amenities, and impeccable design. With a minimum capacity of 8 guests and a maximum capacity of 12, it is an ideal choice for a lavish seaside retreat or special occasions.', 1, 3, 1, 'APPROVED', true, 'APARTMENT', 'ROOM', 'Dag Hammarskjölds torg 14', 'Malmö', 'Sweden', '211 18', false),
    ('Enchanted Forest Cabin', 'Immerse yourself in nature at our Enchanted Forest Cabin. Surrounded by ancient trees and wildlife, this cozy cabin offers a magical escape from the everyday. With a minimum capacity of 2 guests and a maximum capacity of 4, it is perfect for a romantic getaway or a peaceful retreat for nature lovers.', 2, 4, 1, 'APPROVED', true, 'ROOM', 'ROOM', 'Øster Voldgade 8', 'Copenhagen', 'Denmark', '1350', false),
    ('Skyline View Penthouse', 'Indulge in luxury with our Skyline View Penthouse. Perched high above the city, this penthouse offers stunning panoramic views, modern amenities, and a sophisticated ambiance. With a minimum capacity of 4 guests and a maximum capacity of 6, it is perfect for a stylish urban getaway with friends or family.', 4, 5, 3, 'APPROVED', true, 'APARTMENT', 'ROOM', 'Øster Voldgade 9', 'Copenhagen', 'Denmark', '1350', false),
    ('Coastal Clifftop Cottage', 'Perched on a coastal cliff, our Clifftop Cottage offers unrivaled views of the ocean. This charming cottage provides a tranquil and romantic escape, ideal for couples seeking a secluded getaway. With a minimum capacity of 2 guests and a maximum capacity of 3, it is a perfect retreat for those desiring privacy and breathtaking scenery.', 2, 3, 4, 'APPROVED', true, 'ROOM', 'ROOM', 'Øster Voldgade 10', 'Copenhagen', 'Denmark', '1350', false),
    ('Sunny Beachfront Villa', 'Bask in the sun at our Sunny Beachfront Villa. This luxurious villa offers direct access to the beach, private pool, and stunning ocean views. With a minimum capacity of 8 guests and a maximum capacity of 12, it is perfect for a tropical getaway or a seaside celebration.', 3, 5, 5, 'APPROVED', true, 'APARTMENT', 'ROOM', 'Øster Voldgade 11', 'Copenhagen', 'Denmark', '1350', false),
    ('Urban Loft Living', 'Embrace city life in our Urban Loft Living space. Located in the heart of downtown, this modern loft offers stylish design, convenience, and easy access to the vibrant urban scene. With a minimum capacity of 2 guests and a maximum capacity of 4, it is perfect for urban explorers or a chic city retreat.', 2, 4, 2, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Øster Voldgade 12', 'Copenhagen', 'Denmark', '1350', false),
    ('Majestic Mountain Manor', 'Experience grandeur at our Majestic Mountain Manor. Perched high in the mountains, this elegant manor offers opulent interiors, breathtaking views, and a luxurious escape. With a minimum capacity of 10 guests and a maximum capacity of 15, it is an ideal choice for special occasions or an extravagant mountain retreat.', 1, 5, 3, 'APPROVED', true, 'ROOM', 'ROOM', 'Üllői út 1', ' Budapest', 'Hungary', '1091', false),
    ('Riverside Haven Cottage', 'Escape to a Riverside Haven in our charming cottage by the river. Surrounded by nature, this cozy retreat offers tranquility and a connection to the outdoors. With a minimum capacity of 4 guests and a maximum capacity of 6, it is perfect for a family getaway or a peaceful retreat with friends.', 4, 7, 2, 'EDITED', true, 'ROOM', 'ROOM', 'Üllői út 2', ' Budapest', 'Hungary', '1091', false),
    ('Artisanal Village House', 'Discover the charm of village life in our Artisanal Village House. This rustic yet stylish house offers a unique blend of tradition and comfort. With a minimum capacity of 6 guests and a maximum capacity of 8, it is perfect for those seeking an authentic village experience.', 2, 5, 1, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Üllői út 3', ' Budapest', 'Hungary', '1091', false),
    ('Sunny Beachfront Villa', 'Bask in the sun at our Sunny Beachfront Villa. This luxurious villa offers direct access to the beach, private pool, and stunning ocean views. With a minimum capacity of 8 guests and a maximum capacity of 12, it is perfect for a tropical getaway or a seaside celebration.', 1, 4, 1, 'APPROVED', true, 'ROOM', 'ROOM', 'Üllői út 12', ' Budapest', 'Hungary', '1091', false),
    ('Urban Loft Living', 'Embrace city life in our Urban Loft Living space. Located in the heart of downtown, this modern loft offers stylish design, convenience, and easy access to the vibrant urban scene. With a minimum capacity of 2 guests and a maximum capacity of 4, it is perfect for urban explorers or a chic city retreat.', 2, 4, 2, 'APPROVED', true, 'ROOM', 'PERSON', 'Øster Voldgade 1', 'Copenhagen', 'Denmark', '1350', false),
    ('Majestic Mountain Manor', 'Experience grandeur at our Majestic Mountain Manor. Perched high in the mountains, this elegant manor offers opulent interiors, breathtaking views, and a luxurious escape. With a minimum capacity of 10 guests and a maximum capacity of 15, it is an ideal choice for special occasions or an extravagant mountain retreat.', 1, 5, 3, 'APPROVED', true, 'APARTMENT', 'ROOM', 'Øster Voldgade 2', 'Copenhagen', 'Denmark', '1350', false),
    ('Riverside Haven Cottage', 'Escape to a Riverside Haven in our charming cottage by the river. Surrounded by nature, this cozy retreat offers tranquility and a connection to the outdoors. With a minimum capacity of 4 guests and a maximum capacity of 6, it is perfect for a family getaway or a peaceful retreat with friends.', 3, 6, 2, 'APPROVED', true, 'APARTMENT', 'ROOM', 'Øster Voldgade 3', 'Copenhagen', 'Denmark', '1350', false),
    ('Artisanal Village House', 'Discover the charm of village life in our Artisanal Village House. This rustic yet stylish house offers a unique blend of tradition and comfort. With a minimum capacity of 6 guests and a maximum capacity of 8, it is perfect for those seeking an authentic village experience.', 5, 6, 1, 'APPROVED', true, 'HOTEL', 'ROOM', 'Øster Voldgade 13', 'Copenhagen', 'Denmark', '1350', false),
    ('Sunset Cliff Retreat', 'Unwind at our Sunset Cliff Retreat, a hidden gem perched on the cliffs. This serene retreat offers panoramic views of the ocean and a tranquil atmosphere. With a minimum capacity of 6 guests and a maximum capacity of 10, it is perfect for family vacations or a peaceful escape with friends.', 2, 4, 2, 'APPROVED', true, 'HOTEL', 'PERSON', 'Dag Hammarskjölds torg 5', 'Malmö', 'Sweden', '211 18', false),
    ('Rustic Mountain Cabin', 'Escape to nature in our Rustic Mountain Cabin. Tucked away in the hills, this charming cabin provides a cozy retreat surrounded by the beauty of the mountains. With a minimum capacity of 2 guests and a maximum capacity of 4, it is perfect for a romantic getaway or a peaceful mountain escape.', 2, 4, 3, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Dag Hammarskjölds torg 6', 'Malmö', 'Sweden', '211 18', false),
    ('Modern City Apartment', 'Immerse yourself in city living at our Modern City Apartment. Centrally located, this contemporary space offers convenience and comfort. With a minimum capacity of 1 guest and a maximum capacity of 2, it is perfect for solo travelers or a stylish city retreat.', 1, 2, 2, 'APPROVED', true, 'APARTMENT', 'ROOM', 'Dag Hammarskjölds torg 7', 'Malmö', 'Sweden', '211 18', false),
    ('Coastal Cottage Getaway', 'Discover charm by the sea in our Coastal Cottage Getaway. This idyllic cottage offers a peaceful retreat with ocean breezes and sandy shores. With a minimum capacity of 4 guests and a maximum capacity of 6, it is perfect for a family beach vacation or a romantic coastal escape.', 4, 5, 1, 'CREATED', true, 'HOTEL', 'PERSON', 'Dag Hammarskjölds torg 8', 'Malmö', 'Sweden', '211 18', false),
    ('Luxury Riverside Villa', 'Indulge in luxury at our Riverside Villa. This exquisite villa offers a private oasis by the river with upscale amenities and serene surroundings. With a minimum capacity of 8 guests and a maximum capacity of 12, it is perfect for special occasions or a lavish riverside retreat.', 1, 3, 1, 'APPROVED', true, 'HOTEL', 'ROOM', 'Dag Hammarskjölds torg 9', 'Malmö', 'Sweden', '211 18', false);


INSERT INTO pricelist_items (start_date, end_date, price)
VALUES 
    ('2024-03-01', '2024-03-20', 10.99), ('2024-03-25', '2024-03-30', 28.99), ('2024-03-01', '2024-03-10', 39.99), ('2024-03-12', '2024-03-20', 31.99),
    ('2024-03-05', '2024-03-07', 19.99), ('2024-03-10', '2024-03-13', 26.99), ('2024-03-05', '2024-03-12', 31.99), ('2024-03-17', '2024-03-20', 12.99),
    ('2024-03-01', '2024-03-03', 19.99), ('2024-03-06', '2024-03-10', 28.99), ('2024-03-02', '2024-03-05', 37.99), ('2024-03-12', '2024-03-22', 32.99),
    ('2024-03-02', '2024-03-09', 11.99), ('2024-03-11', '2024-03-20', 22.99), ('2024-03-04', '2024-03-08', 34.99), ('2024-03-14', '2024-03-27', 37.99),
    ('2024-03-05', '2024-03-07', 18.99), ('2024-03-10', '2024-03-21', 27.99), ('2024-03-01', '2024-03-09', 32.99), ('2024-03-11', '2024-03-24', 39.99),
    ('2024-03-02', '2024-03-08', 13.99), ('2024-03-12', '2024-03-22', 28.99), ('2024-03-02', '2024-03-05', 33.99), ('2024-03-09', '2024-03-23', 33.99),
    ('2024-03-07', '2024-03-10', 18.99), ('2024-03-13', '2024-03-25', 26.99), ('2024-03-01', '2024-03-03', 33.99), ('2024-03-07', '2024-03-21', 38.99),
    ('2024-03-06', '2024-03-09', 12.99), ('2024-03-11', '2024-03-27', 28.99), ('2024-03-07', '2024-03-10', 35.99), ('2024-03-14', '2024-03-22', 30.99),
    ('2024-03-04', '2024-03-10', 14.99), ('2024-03-13', '2024-03-21', 22.99), ('2024-03-03', '2024-03-09', 30.99), ('2024-03-13', '2024-03-15', 38.99),
    ('2024-03-06', '2024-03-08', 16.99), ('2024-03-11', '2024-03-19', 27.99), ('2024-03-02', '2024-03-09', 32.99), ('2024-03-12', '2024-03-18', 39.99),
    ('2024-03-01', '2024-03-09', 13.99), ('2024-03-12', '2024-03-17', 22.99), ('2024-03-02', '2024-03-07', 36.99), ('2024-03-11', '2024-03-20', 33.99),
    ('2024-03-04', '2024-03-10', 14.99), ('2024-03-13', '2024-03-14', 20.99), ('2024-03-01', '2024-03-06', 39.99), ('2024-03-10', '2024-03-19', 32.99),
    ('2024-03-03', '2024-03-09', 16.99), ('2024-03-12', '2024-03-15', 23.99), ('2024-03-03', '2024-03-09', 37.99), ('2024-03-14', '2024-03-17', 38.99),
    ('2024-03-01', '2024-03-05', 13.99), ('2024-03-10', '2024-03-21', 24.99), ('2024-03-04', '2024-03-09', 30.99), ('2024-03-11', '2024-03-15', 33.99),
    ('2024-03-04', '2024-03-10', 12.99), ('2024-03-13', '2024-03-14', 20.99), ('2024-03-01', '2024-03-06', 35.99), ('2024-03-10', '2024-03-19', 32.99),
    ('2024-03-03', '2024-03-09', 16.99), ('2024-03-12', '2024-03-15', 23.99), ('2024-03-03', '2024-03-09', 32.99), ('2024-03-14', '2024-03-17', 36.99),
    ('2024-03-01', '2024-03-05', 18.99), ('2024-03-10', '2024-03-21', 25.99), ('2024-03-04', '2024-03-09', 32.99), ('2024-03-11', '2024-03-15', 39.99),
    ('2024-03-01', '2024-03-05', 11.99), ('2024-03-10', '2024-03-21', 23.99), ('2024-03-04', '2024-03-09', 39.99), ('2024-03-11', '2024-03-15', 30.99);

INSERT INTO accommodations_price_list (accommodation_id, price_list_id)
VALUES
    (1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 6), (4, 7), (4, 8),
    (5, 9), (5, 10), (6, 11), (6, 12), (7, 13), (7, 14), (8, 15), (8, 16),
    (9, 17), (9, 18), (10, 19), (10, 20), (11, 21), (11, 22), (12, 23), (12, 24),
    (13, 25), (13, 26), (14, 27), (14, 28), (15, 29), (15, 30), (16, 31), (16, 32),
    (17, 33), (17, 34), (18, 35), (18, 36), (19, 37), (19, 38), (20, 39), (20, 40),
    (21, 41), (21, 42), (22, 43), (22, 44), (23, 45), (23, 46), (24, 47), (24, 48),
    (25, 49), (25, 50), (26, 51), (26, 52), (27, 53), (27, 54), (28, 55), (28, 56),
    (29, 57), (29, 58), (30, 59), (30, 60), (31, 61), (31, 62), (32, 63), (32, 64),
    (33, 65), (33, 66), (34, 67), (34, 68), (35, 69), (35, 70), (36, 71), (36, 72);


INSERT INTO availability (start_date, end_date)
VALUES 
    ('2024-03-01', '2024-03-20'), ('2024-03-25', '2024-03-30'), ('2024-03-01', '2024-03-10'), ('2024-03-12', '2024-03-20'),
    ('2024-03-05', '2024-03-07'), ('2024-03-10', '2024-03-13'), ('2024-03-05', '2024-03-12'), ('2024-03-17', '2024-03-20'),
    ('2024-03-01', '2024-03-03'), ('2024-03-06', '2024-03-10'), ('2024-03-02', '2024-03-05'), ('2024-03-12', '2024-03-22'),
    ('2024-03-02', '2024-03-09'), ('2024-03-11', '2024-03-20'), ('2024-03-04', '2024-03-08'), ('2024-03-14', '2024-03-27'),
    ('2024-03-05', '2024-03-07'), ('2024-03-10', '2024-03-21'), ('2024-03-01', '2024-03-09'), ('2024-03-11', '2024-03-24'),
    ('2024-03-02', '2024-03-08'), ('2024-03-12', '2024-03-22'), ('2024-03-02', '2024-03-05'), ('2024-03-09', '2024-03-23'),
    ('2024-03-07', '2024-03-10'), ('2024-03-13', '2024-03-25'), ('2024-03-01', '2024-03-03'), ('2024-03-07', '2024-03-21'),
    ('2024-03-06', '2024-03-09'), ('2024-03-11', '2024-03-27'), ('2024-03-07', '2024-03-10'), ('2024-03-14', '2024-03-22'),
    ('2024-03-04', '2024-03-10'), ('2024-03-13', '2024-03-21'), ('2024-03-03', '2024-03-09'), ('2024-03-13', '2024-03-15'),
    ('2024-03-06', '2024-03-08'), ('2024-03-11', '2024-03-19'), ('2024-03-02', '2024-03-09'), ('2024-03-12', '2024-03-18'),
    ('2024-03-01', '2024-03-09'), ('2024-03-12', '2024-03-17'), ('2024-03-02', '2024-03-07'), ('2024-03-11', '2024-03-20'),
    ('2024-03-04', '2024-03-10'), ('2024-03-13', '2024-03-14'), ('2024-03-01', '2024-03-06'), ('2024-03-10', '2024-03-19'),
    ('2024-03-03', '2024-03-09'), ('2024-03-12', '2024-03-15'), ('2024-03-03', '2024-03-09'), ('2024-03-14', '2024-03-17'),
    ('2024-03-01', '2024-03-05'), ('2024-03-10', '2024-03-21'), ('2024-03-04', '2024-03-09'), ('2024-03-11', '2024-03-15'),
    ('2024-03-04', '2024-03-10'), ('2024-03-13', '2024-03-14'), ('2024-03-01', '2024-03-06'), ('2024-03-10', '2024-03-19'),
    ('2024-03-03', '2024-03-09'), ('2024-03-12', '2024-03-15'), ('2024-03-03', '2024-03-09'), ('2024-03-14', '2024-03-17'),
    ('2024-03-01', '2024-03-05'), ('2024-03-10', '2024-03-21'), ('2024-03-04', '2024-03-09'), ('2024-03-11', '2024-03-15'),
    ('2024-03-01', '2024-03-05'), ('2024-03-10', '2024-03-21'), ('2024-03-04', '2024-03-09'), ('2024-03-11', '2024-03-15');

INSERT INTO accommodations_availability (accommodation_id, availability_id)
VALUES
    (1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 6), (4, 7), (4, 8),
    (5, 9), (5, 10), (6, 11), (6, 12), (7, 13), (7, 14), (8, 15), (8, 16),
    (9, 17), (9, 18), (10, 19), (10, 20), (11, 21), (11, 22), (12, 23), (12, 24),
    (13, 25), (13, 26), (14, 27), (14, 28), (15, 29), (15, 30), (16, 31), (16, 32),
    (17, 33), (17, 34), (18, 35), (18, 36), (19, 37), (19, 38), (20, 39), (20, 40),
    (21, 41), (21, 42), (22, 43), (22, 44), (23, 45), (23, 46), (24, 47), (24, 48),
    (25, 49), (25, 50), (26, 51), (26, 52), (27, 53), (27, 54), (28, 55), (28, 56),
    (29, 57), (29, 58), (30, 59), (30, 60), (31, 61), (31, 62), (32, 63), (32, 64),
    (33, 65), (33, 66), (34, 67), (34, 68), (35, 69), (35, 70), (36, 71), (36, 72);


INSERT INTO reviews (accepted, comment, date, rate, reported, review_type, guest_id)
VALUES 
    (true, 'Great experience!', '2024-03-01 08:00:00', 4, false, 'ACCOMMODATION', 2),
    (true, 'Clean and comfortable.', '2024-03-03 18:45:00', 3, false, 'ACCOMMODATION', 4),
    (true, 'Perfect stay!', '2024-03-05 10:15:00', 5, false, 'ACCOMMODATION', 5),
    (true, 'Lovely place.', '2024-03-07 20:45:00', 4, false, 'ACCOMMODATION', 8),
    (true, 'Cozy atmosphere.', '2024-03-09 15:00:00', 2, false, 'ACCOMMODATION', 10),
    (true, 'Nice location.', '2024-03-11 12:00:00', 1, false, 'ACCOMMODATION', 4),
    (true, 'Spacious rooms.', '2024-03-13 09:30:00', 2, false, 'ACCOMMODATION', 5),
    (true, 'Beautiful view.', '2024-03-15 22:00:00', 5, false, 'ACCOMMODATION', 8),
    (true, 'Homey feel.', '2024-03-17 11:30:00', 4, false, 'ACCOMMODATION', 10),
    (true, 'Good amenities.', '2024-03-19 06:15:00', 3, false, 'ACCOMMODATION', 2),
    (true, 'Cozy atmosphere.', '2024-03-09 15:00:00', 2, false, 'ACCOMMODATION', 2),
    (true, 'Nice location.', '2024-03-11 12:00:00', 1, false, 'ACCOMMODATION', 5),
    (true, 'Spacious rooms.', '2024-03-13 09:30:00', 2, false, 'ACCOMMODATION', 4),
    (true, 'Beautiful view.', '2024-03-15 22:00:00', 5, false, 'ACCOMMODATION', 10),
    (true, 'Homey feel.', '2024-03-17 11:30:00', 4, false, 'ACCOMMODATION', 2),
    (true, 'Good amenities.', '2024-03-19 06:15:00', 3, false, 'ACCOMMODATION', 5),
    (true, 'Beautiful view.', '2024-03-16 22:00:00', 5, false, 'OWNER', 10),
    (true, 'Homey feel.', '2024-03-18 11:30:00', 4, false, 'OWNER', 2),
    (true, 'Good amenities.', '2024-03-20 06:15:00', 3, false, 'OWNER', 5),
    (true, 'Beautiful view.', '2023-03-16 22:00:00', 2, false, 'OWNER', 10),
    (true, 'Homey feel.', '2023-03-18 11:30:00', 1, false, 'OWNER', 2),
    (true, 'Good amenities.', '2023-03-20 06:15:00', 3, false, 'OWNER', 5);

INSERT INTO accommodations_reviews (accommodation_id, reviews_id)
VALUES
    (1, 1), (3, 2), (5, 3), (10, 4), (3, 5), (3, 6), (5, 7), (20, 8),
    (8, 9), (14, 10), (30, 11), (31, 12), (27, 13), (27, 14), (22, 15), (24, 16);

INSERT INTO users_reviews (owner_id, reviews_id)
VALUES
    (3, 17), (3, 18), (7,19), (3, 20), (3, 21), (3, 22);


INSERT INTO accommodation_filters (accommodation_id, filters)
VALUES
    (1, 'AIRPORT_SHUTTLE'), (1, 'DINER'), (1, 'FREE_PARKING'), (1, 'FREE_WIFI'), (1, 'FRONT_DESK'), (1, 'SWIMMING_POOL'), (1, 'NON_SMOKING'), (1, 'BAR'), (1, 'BREAKFAST'), (1, 'CITY_CENTER'),
    (2, 'GARDEN'), (2, 'WHEELCHAIR'), (2, 'PRIVATE_BATHROOM'), (2, 'SWIMMING_POOL'), (2, 'LUNCH'), (2, 'NON_SMOKING'), (2, 'DINER'), (2, 'BAR'), (2, 'BREAKFAST'), (2, 'CITY_CENTER'),
    (3, 'NON_SMOKING'), (3, 'DINER'), (3, 'BAR'), (3, 'FREE_WIFI'), (3, 'CITY_CENTER'), (3, 'DEPOSIT_BOX'), (3, 'WHEELCHAIR'), (3, 'FREE_PARKING'), (3, 'BREAKFAST'), (3, 'FRONT_DESK'),
    (4, 'DEPOSIT_BOX'), (4, 'WHEELCHAIR'), (4, 'FREE_PARKING'), (4, 'BREAKFAST'), (4, 'FRONT_DESK'), (4, 'GARDEN'), (4, 'AIR_CONDITIONING'), (4, 'FREE_PARKING'), (4, 'SWIMMING_POOL'), (4, 'NON_SMOKING'),
    (5, 'AIRPORT_SHUTTLE'), (5, 'HEATING'), (5, 'JACUZZI'), (5, 'FREE_WIFI'), (5, 'CITY_CENTER'), (5, 'GARDEN'), (5, 'TERRACE'), (5, 'BAR'), (5, 'LUNCH'), (5, 'FRONT_DESK'),
    (6, 'GARDEN'), (6, 'AIR_CONDITIONING'), (6, 'FREE_PARKING'), (6, 'SWIMMING_POOL'), (6, 'NON_SMOKING'), (6, 'TERRACE'), (6, 'DINER'), (6, 'BAR'), (6, 'PRIVATE_BATHROOM'), (6, 'FRONT_DESK'),
    (7, 'AIRPORT_SHUTTLE'), (7, 'DINER'), (7, 'FREE_PARKING'), (7, 'FREE_WIFI'), (7, 'FRONT_DESK'), (7, 'SWIMMING_POOL'), (7, 'NON_SMOKING'), (7, 'BAR'), (7, 'BREAKFAST'), (7, 'CITY_CENTER'),
    (8, 'GARDEN'), (8, 'WHEELCHAIR'), (8, 'PRIVATE_BATHROOM'), (8, 'SWIMMING_POOL'), (8, 'LUNCH'), (8, 'NON_SMOKING'), (8, 'DINER'), (8, 'BAR'), (8, 'BREAKFAST'), (8, 'CITY_CENTER'),
    (9, 'NON_SMOKING'), (9, 'DINER'), (9, 'BAR'), (9, 'FREE_WIFI'), (9, 'CITY_CENTER'), (9, 'DEPOSIT_BOX'), (9, 'WHEELCHAIR'), (9, 'FREE_PARKING'), (9, 'BREAKFAST'), (9, 'FRONT_DESK'),
    (10, 'DEPOSIT_BOX'), (10, 'WHEELCHAIR'), (10, 'FREE_PARKING'), (10, 'BREAKFAST'), (10, 'FRONT_DESK'), (10, 'GARDEN'), (10, 'AIR_CONDITIONING'), (10, 'FREE_PARKING'), (10, 'SWIMMING_POOL'), (10, 'NON_SMOKING'),
    (11, 'AIRPORT_SHUTTLE'), (11, 'HEATING'), (11, 'JACUZZI'), (11, 'FREE_WIFI'), (11, 'CITY_CENTER'), (11, 'GARDEN'), (11, 'TERRACE'), (11, 'BAR'), (11, 'LUNCH'), (11, 'FRONT_DESK'),
    (12, 'GARDEN'), (12, 'AIR_CONDITIONING'), (12, 'FREE_PARKING'), (12, 'SWIMMING_POOL'), (12, 'NON_SMOKING'), (12, 'TERRACE'), (12, 'DINER'), (12, 'BAR'), (12, 'PRIVATE_BATHROOM'), (12, 'FRONT_DESK'),
    (13, 'AIRPORT_SHUTTLE'), (13, 'DINER'), (13, 'FREE_PARKING'), (13, 'FREE_WIFI'), (13, 'FRONT_DESK'), (13, 'SWIMMING_POOL'), (13, 'NON_SMOKING'), (13, 'BAR'), (13, 'BREAKFAST'), (13, 'CITY_CENTER'),
    (14, 'GARDEN'), (14, 'WHEELCHAIR'), (14, 'PRIVATE_BATHROOM'), (14, 'SWIMMING_POOL'), (14, 'LUNCH'), (14, 'NON_SMOKING'), (14, 'DINER'), (14, 'BAR'), (14, 'BREAKFAST'), (14, 'CITY_CENTER'),
    (15, 'NON_SMOKING'), (15, 'DINER'), (15, 'BAR'), (15, 'FREE_WIFI'), (15, 'CITY_CENTER'), (15, 'DEPOSIT_BOX'), (15, 'WHEELCHAIR'), (15, 'FREE_PARKING'), (15, 'BREAKFAST'), (15, 'FRONT_DESK'),
    (16, 'DEPOSIT_BOX'), (16, 'WHEELCHAIR'), (16, 'FREE_PARKING'), (16, 'BREAKFAST'), (16, 'FRONT_DESK'), (16, 'GARDEN'), (16, 'AIR_CONDITIONING'), (16, 'FREE_PARKING'), (16, 'SWIMMING_POOL'), (16, 'NON_SMOKING'),
    (17, 'AIRPORT_SHUTTLE'), (17, 'HEATING'), (17, 'JACUZZI'), (17, 'FREE_WIFI'), (17, 'CITY_CENTER'), (17, 'GARDEN'), (17, 'TERRACE'), (17, 'BAR'), (17, 'LUNCH'), (17, 'FRONT_DESK'),
    (18, 'GARDEN'), (18, 'AIR_CONDITIONING'), (18, 'FREE_PARKING'), (18, 'SWIMMING_POOL'), (18, 'NON_SMOKING'), (18, 'TERRACE'), (18, 'DINER'), (18, 'BAR'), (18, 'PRIVATE_BATHROOM'), (18, 'FRONT_DESK'),
    (19, 'AIRPORT_SHUTTLE'), (19, 'DINER'), (19, 'FREE_PARKING'), (19, 'FREE_WIFI'), (19, 'FRONT_DESK'), (19, 'SWIMMING_POOL'), (19, 'NON_SMOKING'), (19, 'BAR'), (19, 'BREAKFAST'), (19, 'CITY_CENTER'),
    (20, 'GARDEN'), (20, 'WHEELCHAIR'), (20, 'PRIVATE_BATHROOM'), (20, 'SWIMMING_POOL'), (20, 'LUNCH'), (20, 'NON_SMOKING'), (20, 'DINER'), (20, 'BAR'), (20, 'BREAKFAST'), (20, 'CITY_CENTER'),
    (21, 'NON_SMOKING'), (21, 'DINER'), (21, 'BAR'), (21, 'FREE_WIFI'), (21, 'CITY_CENTER'), (21, 'DEPOSIT_BOX'), (21, 'WHEELCHAIR'), (21, 'FREE_PARKING'), (21, 'BREAKFAST'), (21, 'FRONT_DESK'),
    (22, 'DEPOSIT_BOX'), (22, 'WHEELCHAIR'), (22, 'FREE_PARKING'), (22, 'BREAKFAST'), (22, 'FRONT_DESK'), (22, 'GARDEN'), (22, 'AIR_CONDITIONING'), (22, 'FREE_PARKING'), (22, 'SWIMMING_POOL'), (22, 'NON_SMOKING'),
    (23, 'AIRPORT_SHUTTLE'), (23, 'HEATING'), (23, 'JACUZZI'), (23, 'FREE_WIFI'), (23, 'CITY_CENTER'), (23, 'GARDEN'), (23, 'TERRACE'), (23, 'BAR'), (23, 'LUNCH'), (23, 'FRONT_DESK'),
    (24, 'GARDEN'), (24, 'AIR_CONDITIONING'), (24, 'FREE_PARKING'), (24, 'SWIMMING_POOL'), (24, 'NON_SMOKING'), (24, 'TERRACE'), (24, 'DINER'), (24, 'BAR'), (24, 'PRIVATE_BATHROOM'), (24, 'FRONT_DESK'),
    (25, 'GARDEN'), (25, 'AIR_CONDITIONING'), (25, 'JACUZZI'), (25, 'LUGGAGE_STORAGE'), (25, 'LUNCH'),
    (26, 'DEPOSIT_BOX'), (26, 'TERRACE'), (26, 'BAR'), (26, 'BREAKFAST'), (26, 'FRONT_DESK'),
    (27, 'NON_SMOKING'), (27, 'DINER'), (27, 'FREE_PARKING'), (27, 'FREE_WIFI'), (27, 'CITY_CENTER'),
    (28, 'PRIVATE_BATHROOM'), (28, 'HEATING'), (28, 'JACUZZI'), (28, 'LUGGAGE_STORAGE'), (28, 'FRONT_DESK'),
    (29, 'DEPOSIT_BOX'), (29, 'AIR_CONDITIONING'), (29, 'FREE_PARKING'), (29, 'BREAKFAST'), (29, 'LUNCH'),
    (30, 'GARDEN'), (30, 'TERRACE'), (30, 'BAR'), (30, 'BREAKFAST'), (30, 'FRONT_DESK'),
    (31, 'AIRPORT_SHUTTLE'), (31, 'DINER'), (31, 'JACUZZI'), (31, 'LUGGAGE_STORAGE'), (31, 'GARDEN'),
    (32, 'GARDEN'), (32, 'DINER'), (32, 'BAR'), (32, 'PRIVATE_BATHROOM'), (32, 'FRONT_DESK'),
    (33, 'AIRPORT_SHUTTLE'), (33, 'HEATING'), (33, 'FREE_PARKING'), (33, 'FREE_WIFI'), (33, 'CITY_CENTER'),
    (34, 'DEPOSIT_BOX'), (34, 'WHEELCHAIR'), (34, 'BAR'), (34, 'SWIMMING_POOL'), (34, 'LUNCH'),
    (35, 'AIRPORT_SHUTTLE'), (35, 'DINER'), (35, 'JACUZZI'), (35, 'SWIMMING_POOL'), (35, 'FRONT_DESK'),
    (36, 'GARDEN'), (36, 'WHEELCHAIR'), (36, 'BAR'), (36, 'FREE_WIFI'), (36, 'LUNCH');


SET @path = 'C:/Users/Dell/Documents/images/';
INSERT INTO image (image_name, image_path)
VALUES
    ('image', CONCAT(@path, '1/1.jpeg')), ('image', CONCAT(@path, '1/2.jpeg')),
    ('image', CONCAT(@path, '2/1.jpeg')), ('image', CONCAT(@path, '2/2.jpeg')),
    ('image', CONCAT(@path, '3/1.jpeg')), ('image', CONCAT(@path, '4/1.jpeg')),
    ('image', CONCAT(@path, '5/1.jpeg')), ('image', CONCAT(@path, '6/1.jpeg')),
    ('image', CONCAT(@path, '7/1.jpeg')), ('image', CONCAT(@path, '8/1.jpeg')),
    ('image', CONCAT(@path, '9/1.jpeg')), ('image', CONCAT(@path, '10/1.jpeg')),
    ('image', CONCAT(@path, '11/1.jpeg')), ('image', CONCAT(@path, '11/2.jpeg')),
    ('image', CONCAT(@path, '12/1.jpeg')), ('image', CONCAT(@path, '12/2.jpeg')),
    ('image', CONCAT(@path, '13/1.jpeg')), ('image', CONCAT(@path, '14/1.jpeg')),
    ('image', CONCAT(@path, '15/1.jpeg')), ('image', CONCAT(@path, '16/1.jpeg')),
    ('image', CONCAT(@path, '17/1.jpeg')), ('image', CONCAT(@path, '18/1.jpeg')),
    ('image', CONCAT(@path, '19/1.jpeg')), ('image', CONCAT(@path, '20/1.jpeg')),
    ('image', CONCAT(@path, '21/1.jpeg')), ('image', CONCAT(@path, '21/2.jpeg')),
    ('image', CONCAT(@path, '22/1.jpeg')), ('image', CONCAT(@path, '22/2.jpeg')),
    ('image', CONCAT(@path, '23/1.jpeg')), ('image', CONCAT(@path, '24/1.jpeg')),
    ('image', CONCAT(@path, '25/1.jpeg')), ('image', CONCAT(@path, '26/1.jpeg')),
    ('image', CONCAT(@path, '27/1.jpeg')), ('image', CONCAT(@path, '28/1.jpeg')),
    ('image', CONCAT(@path, '29/1.jpeg')), ('image', CONCAT(@path, '30/1.jpeg')),
    ('image', CONCAT(@path, '31/1.jpeg')), ('image', CONCAT(@path, '32/1.jpeg')),
    ('image', CONCAT(@path, '33/1.jpeg')), ('image', CONCAT(@path, '34/1.jpeg')),
    ('image', CONCAT(@path, '35/1.jpeg')), ('image', CONCAT(@path, '36/1.jpeg')),
    ('image', CONCAT(@path, '23/2.jpeg')), ('image', CONCAT(@path, '24/2.jpeg')),
    ('image', CONCAT(@path, '25/2.jpeg')), ('image', CONCAT(@path, '27/2.jpeg')),
    ('image', CONCAT(@path, '28/2.jpeg')), ('image', CONCAT(@path, '29/2.jpeg'));

INSERT INTO accommodations_images (accommodation_id, images_id)
VALUES
    (1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (4, 6), (5, 7), (6, 8),
    (7, 9), (8, 10), (9, 11), (10, 12), (11, 13), (11, 14), (12, 15), (12, 16),
    (13, 17), (14, 18), (15, 19), (16, 20), (17, 21), (18, 22), (19, 23), (20, 24),
    (21, 25), (21, 26), (22, 27), (22, 28), (23, 29), (24, 30), (25, 31), (26, 32),
    (27, 33), (28, 34), (29, 35), (30, 36), (31, 37), (32, 38), (33, 39), (34, 40),
    (35, 41), (36, 42), (23, 43), (24, 44), (25, 45), (27, 46), (28, 47), (29, 48);

INSERT INTO users_accommodations (owner_id, accommodations_id)
VALUES
    (3, 1), (6, 2), (7, 3), (9, 4), (3, 5), (6, 6), (7, 7), (9, 8),
    (3, 9), (3, 10), (6, 11), (3, 12), (7, 13), (9, 14), (3, 15), (7, 16),
    (3, 17), (6, 18), (9, 19), (3, 20), (9, 21), (7, 22), (6, 23), (6, 24),
    (6, 25), (6, 26), (7, 27), (3, 28), (9, 29), (7, 30), (6, 31), (6, 32),
    (7, 33), (7, 34), (6, 35), (3, 36);

INSERT INTO reservations (created, start, guest_number, end, status, accommodation_id, guest_id, price)
VALUES
    ('2023-10-01 10:00:00', '2023-11-05', 2, '2023-11-10', 'ACCEPTED', 1, 2, 100),
    ('2023-09-02 14:30:00', '2023-10-08', 1, '2023-10-10', 'ACCEPTED', 1, 4, 200),
    ('2023-06-03 08:45:00', '2023-07-07', 3, '2023-08-02', 'ACCEPTED', 1, 5, 100),
    ('2023-05-04 12:30:00', '2023-06-06', 4, '2023-06-13', 'ACCEPTED', 1, 8, 150),
    ('2023-03-05 20:15:00', '2023-05-09', 2, '2023-05-14', 'ACCEPTED', 1, 2, 120),
    ('2023-04-06 16:30:00', '2023-04-10', 1, '2023-04-15', 'ACCEPTED', 1, 4, 130),
    ('2023-05-04 12:30:00', '2023-06-06', 4, '2023-06-13', 'ACCEPTED', 2, 8, 150),
    ('2023-03-05 20:15:00', '2023-05-09', 2, '2023-05-14', 'ACCEPTED', 5, 2, 120),
    ('2023-04-06 16:30:00', '2023-04-10', 1, '2023-04-15', 'ACCEPTED', 7, 4, 130),
    ('2023-03-05 20:15:00', '2023-05-09', 2, '2023-05-14', 'PENDING', 15, 2, 120),
    ('2023-04-06 16:30:00', '2023-01-10', 1, '2023-01-15', 'REJECTED', 1, 4, 130),
    ('2023-05-04 12:30:00', '2023-06-06', 4, '2023-06-13', 'ACCEPTED', 2, 8, 150),
    ('2023-03-05 20:15:00', '2023-05-09', 2, '2023-05-14', 'PENDING', 10, 2, 120),
    ('2023-04-06 16:30:00', '2023-04-10', 1, '2023-04-15', 'ACCEPTED', 12, 4, 130);

    INSERT INTO pricelist_items (start_date, end_date, price)
VALUES 
    ('2023-11-05', '2023-11-10', 20),
    ('2023-10-08', '2023-10-10', 30),
    ('2023-07-07', '2023-08-02', 10),
    ('2023-06-06', '2023-06-13', 15),
    ('2023-05-09', '2023-05-14', 12),
    ('2023-04-10', '2023-04-15', 13),
    ('2023-06-06', '2023-06-13', 15),
    ('2023-05-09', '2023-05-14', 12),
    ('2023-04-10', '2023-04-15', 13),
    ('2023-05-09', '2023-05-14', 12),
    ('2023-01-10', '2023-01-15', 10),
    ('2023-06-06', '2023-06-13', 16),
    ('2023-05-09', '2023-05-14', 22),
    ('2023-04-10', '2023-04-15', 13);

INSERT INTO accommodations_price_list (accommodation_id, price_list_id)
VALUES
    (1, 73), (1, 74), (1, 75), (1, 76), (1, 77), (1, 78), (2, 79), (5, 80), (7, 81),
    (15, 82), (1, 83), (2, 84), (10, 85), (12, 86);

INSERT INTO availability (start_date, end_date)
VALUES 
    ('2023-05-09', '2023-05-14'), ('2023-05-09', '2023-05-14');

INSERT INTO accommodations_availability (accommodation_id, availability_id)
VALUES
    (15, 73), (10, 74);