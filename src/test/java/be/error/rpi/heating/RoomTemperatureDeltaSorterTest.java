/*-
 * #%L
 * Home Automation
 * %%
 * Copyright (C) 2016 - 2017 Koen Serneels
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package be.error.rpi.heating;

import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import be.error.types.LocationId;

@Test
public class RoomTemperatureDeltaSorterTest {

	private RoomTemperatureDeltaSorter sorter = new RoomTemperatureDeltaSorter();

	public void test() {
		List<RoomTemperature> roomTemperatureInfos = new ArrayList<>();
		roomTemperatureInfos.add(create("20.00", "21.00"));
		roomTemperatureInfos.add(create("19.00", "21.00"));
		roomTemperatureInfos.add(create("18.99", "21.00"));
		roomTemperatureInfos.add(create("20.00", "21.00"));

		List<RoomTemperature> sorted = sorter.sortRoomTemperatureInfos(roomTemperatureInfos);
		assertEquals(sorted.get(0), create("18.99", "21.00"));
		assertEquals(sorted.get(1), create("19.00", "21.00"));
		assertEquals(sorted.get(2), create("20.00", "21.00"));
		assertEquals(sorted.get(3), create("20.00", "21.00"));

		roomTemperatureInfos = new ArrayList<>();
		roomTemperatureInfos.add(create("20.00", "21.00"));
		roomTemperatureInfos.add(create("19.00", "21.00"));
		roomTemperatureInfos.add(create("27.00", "21.00"));
		roomTemperatureInfos.add(create("25.00", "21.00"));
		roomTemperatureInfos.add(create("18.99", "21.00"));
		roomTemperatureInfos.add(create("20.00", "21.00"));
		roomTemperatureInfos.add(create("24.00", "21.00"));

		sorted = sorter.sortRoomTemperatureInfos(roomTemperatureInfos);
		assertEquals(sorted.get(0), create("18.99", "21.00"));
		assertEquals(sorted.get(1), create("19.00", "21.00"));
		assertEquals(sorted.get(2), create("20.00", "21.00"));
		assertEquals(sorted.get(3), create("20.00", "21.00"));
		assertEquals(sorted.get(4), create("24.00", "21.00"));
		assertEquals(sorted.get(5), create("25.00", "21.00"));
		assertEquals(sorted.get(6), create("27.00", "21.00"));

		roomTemperatureInfos = new ArrayList<>();
		roomTemperatureInfos.add(create("20.00", "21.00"));
		roomTemperatureInfos.add(create("19.00", "21.00"));
		roomTemperatureInfos.add(create("27.00", "21.00"));
		roomTemperatureInfos.add(create("25.00", "21.00"));
		roomTemperatureInfos.add(create("18.99", "21.00"));
		roomTemperatureInfos.add(create("20.00", "21.00"));
		roomTemperatureInfos.add(create("21.00", "21.00"));
		roomTemperatureInfos.add(create("24.00", "21.00"));

		sorted = sorter.sortRoomTemperatureInfos(roomTemperatureInfos);
		assertEquals(sorted.get(0), create("18.99", "21.00"));
		assertEquals(sorted.get(1), create("19.00", "21.00"));
		assertEquals(sorted.get(2), create("20.00", "21.00"));
		assertEquals(sorted.get(3), create("20.00", "21.00"));
		assertEquals(sorted.get(4), create("21.00", "21.00"));
		assertEquals(sorted.get(5), create("24.00", "21.00"));
		assertEquals(sorted.get(6), create("25.00", "21.00"));
		assertEquals(sorted.get(7), create("27.00", "21.00"));
	}

	private RoomTemperature create(String current, String desired) {
		RoomTemperature roomTemperature = new RoomTemperature(LocationId.BADKAMER, new BigDecimal(current).setScale(2), new BigDecimal(desired).setScale(2));
		return roomTemperature;
	}
}
