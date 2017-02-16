CREATE TABLE `USER` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(200) NOT NULL,
  `email` varchar(45),
  `first_name` varchar(45),
  `last_name` varchar(45),
  PRIMARY KEY (`id`)
);
INSERT into USER VALUES (default, 'admin', 'ooGnBop72Wjk/GCK3PRvCHOYJEFLn8XaU71Zu6OjXHY=$FCdMearGcDPcDeKJYNqmLWcZlAo++OfT7nENx7BrCE0=', 'majeed.thaika@hotmail.com', 'Majeed', 'Thaika');
