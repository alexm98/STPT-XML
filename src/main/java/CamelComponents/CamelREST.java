package CamelComponents;

import Models.TimeTable;
import Models.TransportStation;
import Models.Vehicle;
import core.StationsInteractor;
import core.TimeTablesInteractor;
import core.VehiclesInteractor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import parsers.ParserUtils;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

@Component
public class CamelREST extends RouteBuilder {
    private final StationsInteractor stations_interactor;
    private final VehiclesInteractor vehicles_interactor;
    private final TimeTablesInteractor timetables_interactor;
    private final ParserUtils putils;

    public CamelREST() throws JAXBException, ParserConfigurationException {
        this.vehicles_interactor = new VehiclesInteractor("data/vehicles.xml");
        this.timetables_interactor = new TimeTablesInteractor("data/timetables.xml");
        this.stations_interactor = new StationsInteractor("data/statii-ratt.xml");
        this.putils = new ParserUtils("data/statii-ratt.xml");
    }

    @Override
    public void configure() throws Exception {
        restConfiguration().component("netty-http")
                .host("localhost")
                .port("9091")
                .bindingMode(RestBindingMode.auto);

        rest("/api")
                // Begin: Stations REST endpoints
                .get("/stations")
                .produces("application/xml")
                .route()
                .bean(stations_interactor, "getAllStations")
                .endRest()

                .get("/station/{id}")
                .produces("application/xml")
                .route()
                .bean(stations_interactor, "getStation(${header.id})")
                .endRest()

                .put("/station/{id}")
                .type(TransportStation.class)
                .consumes("application/xml")
                .produces("application/xml")
                .route()
                .bean(stations_interactor, "replaceStation(${header.id}, ${body})")
                .endRest()

                .post("/station")
                .type(TransportStation.class)
                .consumes("application/xml")
                .produces("application/xml")
                .route()
                .bean(stations_interactor, "createStation(${body})")
                .endRest()

                .delete("/station/{id}")
                .produces("application/xml")
                .route()
                .bean(stations_interactor, "deleteStation(${header.id})")
                .endRest()

                // Begin: Vehicles REST endpoints

                .get("/vehicles")
                .produces("application/xml")
                .route()
                .bean(vehicles_interactor, "getAllVehicles")
                .endRest()

                .get("/vehicle/{id}")
                .produces("application/xml")
                .route()
                .bean(vehicles_interactor, "getVehicle(${header.id})")
                .endRest()

                .put("/vehicle/{id}")
                .type(Vehicle.class)
                .consumes("application/xml")
                .produces("application/xml")
                .route()
                .bean(vehicles_interactor, "replaceVehicle(${header.id}, ${body})")
                .endRest()

                .post("/vehicle")
                .type(Vehicle.class)
                .consumes("application/xml")
                .produces("application/xml")
                .route()
                .bean(vehicles_interactor, "createVehicle(${body})")
                .endRest()

                .delete("/vehicle/{id}")
                .produces("application/xml")
                .route()
                .bean(vehicles_interactor, "deleteVehicle(${header.id})")
                .endRest()

                // Begin: TimeTables REST endpoints
                .get("/timetables")
                .produces("application/xml")
                .route()
                .bean(timetables_interactor, "getAllTimeTables")
                .endRest()

                .get("/timetable/{id}")
                .produces("application/xml")
                .route()
                .bean(timetables_interactor, "getTimeTable(${header.id})")
                .endRest()

//                .put("/timetable/{id}")
//                .type(TimeTable.class)
//                .consumes("application/xml")
//                .produces("application/xml")
//                .route()
//                .bean(vehicles_interactor, "replaceTimeTable(${header.id}, ${body})")
//                .endRest()

                .post("/timetable")
                .type(TimeTable.class)
                .consumes("application/xml")
                .produces("application/xml")
                .route()
                .bean(timetables_interactor, "createTimeTable(${body})")
                .endRest();
//
//                .delete("/vehicle/{id}")
//                .produces("application/xml")
//                .route()
//                .bean(vehicles_interactor, "deleteVehicle(${header.id})")
//                .endRest();
    }
};
