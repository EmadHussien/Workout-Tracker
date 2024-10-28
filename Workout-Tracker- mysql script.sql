-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema workout_tracker
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema workout_tracker
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `workout_tracker` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `workout_tracker` ;

-- -----------------------------------------------------
-- Table `workout_tracker`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workout_tracker`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(50) NULL DEFAULT NULL,
  `lastname` VARCHAR(50) NULL DEFAULT NULL,
  `age` INT NULL DEFAULT NULL,
  `email` VARCHAR(250) NOT NULL,
  `height` FLOAT NULL DEFAULT NULL,
  `weight` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `workout_tracker`.`workout_plans`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workout_tracker`.`workout_plans` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `plan_name` VARCHAR(100) NOT NULL,
  `userid` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `userid_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `userid`
    FOREIGN KEY (`userid`)
    REFERENCES `workout_tracker`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `workout_tracker`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workout_tracker`.`comments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `comment` VARCHAR(255) NULL DEFAULT NULL,
  `planid` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `planid_comment_idx` (`planid` ASC) VISIBLE,
  CONSTRAINT `planid_comment`
    FOREIGN KEY (`planid`)
    REFERENCES `workout_tracker`.`workout_plans` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `workout_tracker`.`exercies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workout_tracker`.`exercies` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `url_photo` VARCHAR(450) NULL DEFAULT NULL,
  `muscle` VARCHAR(50) NULL DEFAULT NULL,
  `category` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `workout_tracker`.`workout_plans_exercies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workout_tracker`.`workout_plans_exercies` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sets` INT NULL DEFAULT NULL,
  `repetitions` INT NULL DEFAULT NULL,
  `weight` INT NULL DEFAULT NULL,
  `planid` INT NULL DEFAULT NULL,
  `exerciseid` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `plandid-exercise_idx` (`planid` ASC) VISIBLE,
  INDEX `exerciseid_idx` (`exerciseid` ASC) VISIBLE,
  CONSTRAINT `exerciseid`
    FOREIGN KEY (`exerciseid`)
    REFERENCES `workout_tracker`.`exercies` (`id`),
  CONSTRAINT `plandid-exercise`
    FOREIGN KEY (`planid`)
    REFERENCES `workout_tracker`.`workout_plans` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `workout_tracker`.`workout_sessions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workout_tracker`.`workout_sessions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `scheduledAt` DATETIME NULL DEFAULT NULL,
  `duration` FLOAT NULL DEFAULT NULL,
  `status` ENUM('scheduled', 'completed', 'missed') NOT NULL,
  `userid` INT NULL DEFAULT NULL,
  `planid` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `userid_idx` (`userid` ASC) VISIBLE,
  INDEX `planid_idx` (`planid` ASC) VISIBLE,
  CONSTRAINT `planid`
    FOREIGN KEY (`planid`)
    REFERENCES `workout_tracker`.`workout_plans` (`id`),
  CONSTRAINT `userid_session`
    FOREIGN KEY (`userid`)
    REFERENCES `workout_tracker`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
