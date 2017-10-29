package be.error.rpi.heating;

import static be.error.rpi.config.RunConfig.getInstance;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tuwien.auto.calimero.GroupAddress;

import be.error.types.LocationId;

public class RoomValveController {

	private static final Logger logger = LoggerFactory.getLogger("heating");

	private LocationId locationId;

	private Collection<GroupAddress> valves = new ArrayList<>();

	private Optional<Boolean> lastSendValveState = empty();

	public RoomValveController(final LocationId locationId, final Collection<GroupAddress> valves) {
		this.locationId = locationId;
		this.valves = valves;
	}

	public void updateIfNeeded(boolean heatingDemand) {
		if (lastSendValveState.isPresent() && lastSendValveState.get() == heatingDemand) {
			logger.debug("Not sending valve update for room " + locationId + " last send:" + lastSendValveState + " current::" + heatingDemand);
			return;
		}

		try {
			getInstance().getKnxConnectionFactory().runWithProcessCommunicator(pc -> {
				for (GroupAddress ga : valves) {
					logger.debug("Setting valve " + ga.toString() + " of room " + locationId + " to:" + heatingDemand);
					pc.write(ga, heatingDemand);
				}
				lastSendValveState = of(heatingDemand);
			});
		} catch (Exception e) {
			logger.error("Could not operate valve via KNX", e);
			throw new RuntimeException(e);
		}
	}

	LocationId getLocationId() {
		return locationId;
	}
}
