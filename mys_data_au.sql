-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 03, 2018 at 05:05 AM
-- Server version: 10.1.28-MariaDB
-- PHP Version: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mys_data_au`
--

-- --------------------------------------------------------

--
-- Table structure for table `buyer`
--

CREATE TABLE `buyer` (
  `buyer_id` int(11) NOT NULL,
  `buyer_name` varchar(30) DEFAULT NULL,
  `buyer_mobile` varchar(15) DEFAULT NULL,
  `address` longtext,
  `email` varchar(50) DEFAULT NULL,
  `nowdate` varchar(50) DEFAULT NULL,
  `payed_amount` decimal(20,2) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `Member_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `buyer`
--

INSERT INTO `buyer` (`buyer_id`, `buyer_name`, `buyer_mobile`, `address`, `email`, `nowdate`, `payed_amount`, `user_id`, `Member_id`) VALUES
(1, '', '', '', '', '31/12/2017', '267.24', 1, NULL),
(2, '', '', '', '', '31/12/2017', '12480.00', 1, NULL),
(3, '', '', '', '', '31/12/2017', '500.00', 1, NULL),
(4, '', '', '', '', '31/12/2017', '500.00', 1, NULL),
(5, '', '', '', '', '31/12/2017', '400.00', 1, NULL),
(6, '', '', '', '', '21/12/2017', '267.24', 1, NULL),
(7, '', '', '', '', '22/12/2017', '500.00', 7, NULL),
(8, '', '', '', '', '22/12/2017', '12478.68', 6, NULL),
(9, 'Ahosan Ullah', '', '', '', '22/12/2017', '600.00', 1, 1),
(10, '', '', '', '', '23/12/2017', '2000.00', 1, NULL),
(11, 'ami', '', '', '', '25/12/2017', '500.00', 1, NULL),
(12, '', '', '', '', '26/12/2017', '500.00', 1, NULL),
(13, '', '', '', '', '27/12/2017', '500.00', 1, NULL),
(14, 'Md. Ahosan Ullah', '+8801815172500', '', '', '27/12/2017', '1500.00', 1, NULL),
(15, '', '', '', '', '27/12/2017', '1000.00', 1, NULL),
(16, '', '', '', '', '28/12/2017', '1000.00', 2, NULL),
(17, '', '', '', '', '28/12/2017', '600.00', 1, NULL),
(18, '', '', '', '', '28/12/2017', '1000.00', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `buy_product_list`
--

CREATE TABLE `buy_product_list` (
  `buyer_id` int(11) NOT NULL,
  `pro_id` int(11) NOT NULL,
  `sell_price` decimal(10,2) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `discount` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `buy_product_list`
--

INSERT INTO `buy_product_list` (`buyer_id`, `pro_id`, `sell_price`, `quantity`, `discount`) VALUES
(1, 1, '250.00', 1, '2.00'),
(1, 2, '12.00', 1, '0.00'),
(5, 1, '250.00', 1, '2.00'),
(6, 1, '250.00', 1, '2.00'),
(6, 2, '12.00', 1, '0.00'),
(7, 1, '250.00', 1, '2.00'),
(7, 2, '12.00', 1, '0.00'),
(8, 11, '12.00', 1, '1.00'),
(8, 12, '12222.00', 1, '1.00'),
(9, 1, '250.00', 1, '2.00'),
(9, 2, '12.00', 1, '0.00'),
(9, 5, '255.00', 1, '2.00'),
(10, 1, '250.00', 4, '2.00'),
(11, 1, '250.00', 1, '2.00'),
(12, 1, '250.00', 1, '2.00'),
(12, 2, '12.00', 1, '0.00'),
(13, 1, '249.00', 1, '2.00'),
(14, 1, '250.00', 1, '2.00'),
(14, 2, '12.00', 1, '0.00'),
(14, 5, '255.00', 1, '2.00'),
(14, 9, '150.00', 5, '10.00'),
(14, 10, '12.00', 9, '0.00'),
(15, 1, '250.00', 2, '2.00'),
(15, 2, '12.00', 1, '0.00'),
(15, 5, '255.00', 1, '2.00'),
(15, 9, '150.00', 1, '10.00'),
(16, 1, '250.00', 3, '2.00'),
(17, 1, '250.00', 2, '2.00'),
(17, 2, '12.00', 1, '0.00'),
(18, 1, '250.00', 2, '2.00'),
(18, 2, '12.00', 1, '0.00'),
(18, 5, '255.00', 1, '2.00');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `cat_id` int(11) NOT NULL,
  `cat_name` varchar(30) DEFAULT NULL,
  `owner_id` int(11) NOT NULL,
  `Discount` decimal(3,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`cat_id`, `cat_name`, `owner_id`, `Discount`) VALUES
(1, 'Dairy', 1, '1.60'),
(2, 'hello tata', 1, '1.00'),
(5, 'xy', 1, '0.00'),
(8, 'Hello hi', 1, '1.00'),
(9, 'Good Food', 6, '0.00'),
(10, 'Royal Good', 6, '0.00'),
(11, 'Cini food', 6, '0.00'),
(12, 'Nai food', 6, '0.00');

-- --------------------------------------------------------

--
-- Table structure for table `in_out`
--

CREATE TABLE `in_out` (
  `user_id` int(11) NOT NULL,
  `withdrawAmount` decimal(10,2) DEFAULT NULL,
  `in_time` varchar(15) DEFAULT NULL,
  `out_time` varchar(15) DEFAULT NULL,
  `nowdate` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `in_out`
--

INSERT INTO `in_out` (`user_id`, `withdrawAmount`, `in_time`, `out_time`, `nowdate`) VALUES
(1, NULL, '12:12:am', '12:12:am', '12/12/12'),
(2, NULL, '12:12:00 am', '12:12:am', '12/12/12');

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `member_id` int(11) NOT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `member_name` varchar(30) DEFAULT NULL,
  `point` decimal(11,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`member_id`, `owner_id`, `member_name`, `point`) VALUES
(1, 1, 'ahosan', '1'),
(2, 6, 'asdg', '30'),
(3, 1, 'Samir', '20');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `pro_id` int(11) NOT NULL,
  `pro_name` varchar(30) DEFAULT NULL,
  `pro_description` longtext,
  `in_date` varchar(30) DEFAULT NULL,
  `exp_date` varchar(30) DEFAULT NULL,
  `sell_price` decimal(10,2) DEFAULT NULL,
  `net_price` decimal(10,2) DEFAULT NULL,
  `discount` varchar(5) DEFAULT NULL,
  `cat_id` int(11) NOT NULL,
  `unit` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`pro_id`, `pro_name`, `pro_description`, `in_date`, `exp_date`, `sell_price`, `net_price`, `discount`, `cat_id`, `unit`) VALUES
(1, 'Bega Gourmet Slices Cheese', '200 gm', '05/12/2017', '15/12/2018', '250.00', '249.00', '2', 1, 1),
(2, 'Bon Apetit Sliced Cheese', 'Bon Apetit Sliced Cheese', '04/12/2017', '16/12/2018', '12.00', '10.00', '0', 1, 2),
(5, 'Bega Gourmet Slices Cheese', '200 gm', '05/12/2017', '15/12/2018', '255.00', '249.00', '2', 1, 122),
(9, 'Amar Pro duct', '', '05/12/2017', '12/12/2017', '150.00', '100.00', '10', 1, 2),
(10, 'asjgdha', '', '01/12/2017', '31/12/2017', '12.00', '21.00', '0', 2, 0),
(11, 'asdkjs', '', '20/12/2017', '19/12/2017', '12.00', '12.00', '1', 9, 0),
(12, 'dada', '', '19/12/2017', '26/12/2017', '12222.00', '232.00', '1', 10, 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(10) NOT NULL,
  `password` varchar(100) NOT NULL,
  `type` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `full_name` varchar(50) DEFAULT NULL,
  `address` longtext,
  `permanent_address` longtext,
  `salary` decimal(20,0) NOT NULL,
  `phone` varchar(36) DEFAULT NULL,
  `photo` tinyint(1) DEFAULT NULL,
  `dateofbirth` varchar(10) DEFAULT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `sex` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `password`, `type`, `email`, `full_name`, `address`, `permanent_address`, `salary`, `phone`, `photo`, `dateofbirth`, `owner_id`, `sex`) VALUES
(1, 'admin', 'admin', 'admin', 'Admin@aaa.com', 'Main Admin', 'admin', 'Dhaka', '123', '01815172500', 1, '28/12/1986', 1, 'Male'),
(2, 'ahosan', '1234', 'vendor', 'abc@gmail.com', 'Md. Ahosan Ullah', 'as', 'vd', '123456', '121212', 0, '10/12/1992', 1, 'Male'),
(6, 'aman', '1234', 'admin', 'Admin@aaa.com', 'Main Admin 2', 'Ahosan', 'ullah', '12000', '+880181517250', 0, '12/11/2017', 6, 'Female'),
(7, 'asa', '12345', 'manager', 'xax', 'ahosan', 'cs', 'ffs', '1313', '1212', 0, '20/12/2017', 1, 'Male'),
(8, 'asdasdsad', '12345', 'null', 'dasdsad', 'hello', 'scczc', 'czzc', '1212', '1111111111111', 1, '12/12/1992', 1, 'Male'),
(9, 'dsadsad', '12345', 'admin', 'sdasd', 'new user', 'dsdsdsd', 'sdsdsd', '12121', '121212', 1, '12/12/1992', 1, 'Male');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `buyer`
--
ALTER TABLE `buyer`
  ADD PRIMARY KEY (`buyer_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `buy_product_list`
--
ALTER TABLE `buy_product_list`
  ADD PRIMARY KEY (`buyer_id`,`pro_id`),
  ADD KEY `pro_id` (`pro_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`cat_id`),
  ADD KEY `owner_id` (`owner_id`);

--
-- Indexes for table `in_out`
--
ALTER TABLE `in_out`
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`member_id`),
  ADD KEY `owner_id` (`owner_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`pro_id`),
  ADD KEY `cat_id` (`cat_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `owner_id` (`owner_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `buyer`
--
ALTER TABLE `buyer`
  MODIFY `buyer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `cat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `member`
--
ALTER TABLE `member`
  MODIFY `member_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `pro_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `buyer`
--
ALTER TABLE `buyer`
  ADD CONSTRAINT `buyer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `buy_product_list`
--
ALTER TABLE `buy_product_list`
  ADD CONSTRAINT `buy_product_list_ibfk_1` FOREIGN KEY (`buyer_id`) REFERENCES `buyer` (`buyer_id`),
  ADD CONSTRAINT `buy_product_list_ibfk_2` FOREIGN KEY (`pro_id`) REFERENCES `product` (`pro_id`);

--
-- Constraints for table `category`
--
ALTER TABLE `category`
  ADD CONSTRAINT `category_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `in_out`
--
ALTER TABLE `in_out`
  ADD CONSTRAINT `in_out_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `member`
--
ALTER TABLE `member`
  ADD CONSTRAINT `member_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`cat_id`) REFERENCES `category` (`cat_id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
