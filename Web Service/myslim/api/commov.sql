-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 08-Maio-2021 às 20:20
-- Versão do servidor: 10.4.17-MariaDB
-- versão do PHP: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `commov`
--
CREATE DATABASE IF NOT EXISTS `commov` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `commov`;

-- --------------------------------------------------------

--
-- Estrutura da tabela `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `category`
--

INSERT INTO `category` (`id`, `name`, `description`) VALUES
(1, 'Passeios e Acessibilidades', 'Passeios e Acessibilidades');

-- --------------------------------------------------------

--
-- Estrutura da tabela `ocorrency`
--

DROP TABLE IF EXISTS `ocorrency`;
CREATE TABLE `ocorrency` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `subCategory_id` int(11) NOT NULL,
  `foto` varchar(200) NOT NULL,
  `street` varchar(200) NOT NULL,
  `reference_point` varchar(200) NOT NULL,
  `description` varchar(200) NOT NULL,
  `latitude` varchar(200) NOT NULL,
  `longitude` varchar(200) NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `ocorrency`
--

INSERT INTO `ocorrency` (`id`, `user_id`, `category_id`, `subCategory_id`, `foto`, `street`, `reference_point`, `description`, `latitude`, `longitude`, `date`) VALUES
(8, 1, 1, 1, 'foto', 'Rua Banana', 'Muita banana', 'As bananas estao podres', '41.6936', '-8.8476', '2021-05-18 19:16:41'),
(9, 1, 1, 1, 'Uma foto', 'Rua dos leds', 'ha beira do bar dos bebados', 'Os leds nao funcionam', '41.691099', '-8.827746', '2021-03-14 21:10:00');

-- --------------------------------------------------------

--
-- Estrutura da tabela `subcategory`
--

DROP TABLE IF EXISTS `subcategory`;
CREATE TABLE `subcategory` (
  `id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `subcategory`
--

INSERT INTO `subcategory` (`id`, `category_id`, `name`, `description`) VALUES
(1, 1, 'Abatimentos Superficiais', 'Abatimentos Superficiais');

-- --------------------------------------------------------

--
-- Estrutura da tabela `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(200) NOT NULL,
  `first_name` varchar(200) NOT NULL,
  `last_name` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `notifications` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `user`
--

INSERT INTO `user` (`id`, `username`, `first_name`, `last_name`, `email`, `password`, `notifications`) VALUES
(1, 'gabrielSousa', 'Gabriel', 'Sousa', 'gabrielsousa@ipvc.pt', '1243', 0),
(2, 'teste', 'Clara', 'Esteves', 'clr@gmail.com', '1243', 0),
(3, '123', '123', '12312', '213', '123', 0);

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `ocorrency`
--
ALTER TABLE `ocorrency`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_Ocorrency_Category` (`category_id`),
  ADD KEY `FK_Ocorrency_User` (`user_id`),
  ADD KEY `FK_Ocorrency_SubCategory` (`subCategory_id`);

--
-- Índices para tabela `subcategory`
--
ALTER TABLE `subcategory`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_subCategory_Category` (`category_id`);

--
-- Índices para tabela `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `ocorrency`
--
ALTER TABLE `ocorrency`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de tabela `subcategory`
--
ALTER TABLE `subcategory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `ocorrency`
--
ALTER TABLE `ocorrency`
  ADD CONSTRAINT `FK_Ocorrency_Category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  ADD CONSTRAINT `FK_Ocorrency_SubCategory` FOREIGN KEY (`subCategory_id`) REFERENCES `subcategory` (`id`),
  ADD CONSTRAINT `FK_Ocorrency_User` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Limitadores para a tabela `subcategory`
--
ALTER TABLE `subcategory`
  ADD CONSTRAINT `FK_subCategory_Category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
