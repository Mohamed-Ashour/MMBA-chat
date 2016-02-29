-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 27, 2016 at 12:32 PM
-- Server version: 5.5.47-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


CREATE DATABASE Chat;
USE Chat;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `Chat`
--

-- --------------------------------------------------------

--
-- Table structure for table `Contact`
--

CREATE TABLE IF NOT EXISTS `Contact` (
  `user` char(50) NOT NULL,
  `contact` char(50) NOT NULL,
  PRIMARY KEY (`user`,`contact`),
  KEY `contact` (`contact`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Message`
--

CREATE TABLE IF NOT EXISTS `Message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sessionId` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `from` char(50) NOT NULL,
  `message` text NOT NULL,
  `delivered` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`,`sessionId`),
  KEY `sessionId` (`sessionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `Session`
--

CREATE TABLE IF NOT EXISTS `Session` (
  `sessionId` int(11) NOT NULL AUTO_INCREMENT,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  PRIMARY KEY (`sessionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `SessionUser`
--

CREATE TABLE IF NOT EXISTS `SessionUser` (
  `sessionId` int(11) NOT NULL,
  `user` char(50) NOT NULL,
  PRIMARY KEY (`sessionId`,`user`),
  KEY `user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `name` char(30) NOT NULL,
  `username` char(30) NOT NULL,
  `email` char(50) NOT NULL,
  `password` char(128) NOT NULL,
  `country` char(30) NOT NULL,
  `gender` enum('male','female') NOT NULL,
  `status` char(30) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Contact`
--
ALTER TABLE `Contact`
  ADD CONSTRAINT `Contact_ibfk_2` FOREIGN KEY (`contact`) REFERENCES `User` (`email`),
  ADD CONSTRAINT `Contact_ibfk_1` FOREIGN KEY (`user`) REFERENCES `User` (`email`);

--
-- Constraints for table `Message`
--
ALTER TABLE `Message`
  ADD CONSTRAINT `Message_ibfk_1` FOREIGN KEY (`sessionId`) REFERENCES `Session` (`sessionId`);

--
-- Constraints for table `SessionUser`
--
ALTER TABLE `SessionUser`
  ADD CONSTRAINT `SessionUser_ibfk_2` FOREIGN KEY (`user`) REFERENCES `User` (`email`),
  ADD CONSTRAINT `SessionUser_ibfk_1` FOREIGN KEY (`sessionId`) REFERENCES `Session` (`sessionId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
