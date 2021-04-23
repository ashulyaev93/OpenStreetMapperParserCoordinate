package com.test.geodata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Спроектировать и реализовать REST сервис на Java. Сервис должен получать данные с сервиса OSM (Open Street Map)
 * который возвращает географические данные (множество координатных точек описывающих географическое положение объекта)
 * какого-либо субъекта Российской Федерации. Класс должен возвращать массив координат наибольшей части гео-объекта по
 * его названию и типу (например название “Самарская область”, тип “region”), а также вычислять положение географического
 * центра полученного массива координат. Данные должны кэшироваться во избежание повторных запросов к OSM сервису */

@SpringBootApplication
public class GeodataApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeodataApplication.class, args);
	}
}
