-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 27, 2025 at 08:01 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `findmydorm`
--

-- --------------------------------------------------------

--
-- Table structure for table `dorms`
--

CREATE TABLE `dorms` (
  `id` int(11) NOT NULL,
  `user_phone` varchar(20) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `short_description` text DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `dorm_name` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dorms`
--

INSERT INTO `dorms` (`id`, `user_phone`, `price`, `short_description`, `location`, `dorm_name`) VALUES
(5, '1346', 200.00, '2 bedrooms, one toilet for each bedroom', 'Beirut', 'Dorma Beirut'),
(7, '147369', 1500.00, 'Caf', 'Lau', 'Cafeteria'),
(8, '147369', 2.00, '12', 'Library', 'Testaa'),
(13, '1346', 20.00, 'An empty class to study in', 'LAU', 'Nicol 408');

-- --------------------------------------------------------

--
-- Table structure for table `dorm_images`
--

CREATE TABLE `dorm_images` (
  `id` int(11) NOT NULL,
  `dorm_id` int(11) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dorm_images`
--

INSERT INTO `dorm_images` (`id`, `dorm_id`, `image_path`) VALUES
(4, 5, 'uploads/1745562505_upload.jpg'),
(6, 7, 'uploads/1745570571_upload.jpg'),
(7, 8, 'uploads/1745575865_upload.jpg'),
(12, 13, 'uploads/1745776803_upload.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `phone`, `password`) VALUES
(1, 'test', '1234567', '$2y$10$UXa2g7FTdbOaZfR5EmF8cu4pGhFAKGd81qlElxkj4CPNWAUeGdSI6'),
(2, 'Testa', '123', '$2y$10$g6jdy57rZdcoUO872/vt6.meCjhOPtsAHYUpJ6rT8JYIoij0xkP26'),
(3, 'Abdallah', '1298', '$2y$10$V78FPVvKtRh1SJzuiRWRleVlHZTcqIIA4SDNtslXdAxTK3tth.Z3S'),
(4, 'Abdallah Ajram', '1346', '$2y$10$40CbQmLus81X77LXQQ3Y1uz/5lU7ZFJnwlxJhry0mZ9/TPjPdlqES'),
(5, 'TestUser', '147369', '$2y$10$TQpWZvYfyuFqGg6XOJOO6eQQ82czi/PhyVef1h/bhsakkI30sNUvS'),
(10, 'TestV', '147', '$2y$10$Y6FVuS.hkIlBLc/cUy97j.vqQNy6Y3Y6zX0v7FTLiPxWy2aWYxz9K'),
(11, 'Final TEST', '1469', '$2y$10$.2vCoxJCXz3zluoFEzaKBuz5Xq5khW2Kd24644cgqxOiIETQo/OoC');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dorms`
--
ALTER TABLE `dorms`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_phone` (`user_phone`);

--
-- Indexes for table `dorm_images`
--
ALTER TABLE `dorm_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `dorm_id` (`dorm_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `phone` (`phone`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dorms`
--
ALTER TABLE `dorms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `dorm_images`
--
ALTER TABLE `dorm_images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `dorms`
--
ALTER TABLE `dorms`
  ADD CONSTRAINT `dorms_ibfk_1` FOREIGN KEY (`user_phone`) REFERENCES `users` (`phone`);

--
-- Constraints for table `dorm_images`
--
ALTER TABLE `dorm_images`
  ADD CONSTRAINT `dorm_images_ibfk_1` FOREIGN KEY (`dorm_id`) REFERENCES `dorms` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
