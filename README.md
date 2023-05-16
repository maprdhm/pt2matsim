# PT2MATSim

[![Build Status](https://travis-ci.org/matsim-org/pt2matsim.svg?branch=master)](https://travis-ci.org/matsim-org/pt2matsim)

PT2MATSim is a package to convert public transit data from GTFS, HAFAS or OSM to a completely mapped MATSim schedule.

![Mapping example](doc/mapping-example.png)

There are multiple public transit schedule data formats, widely used formats are GTFS and HAFAS. Numerous GTFS feeds are
publicly available (see [transitfeeds.com](https://transitfeeds.com) or [transit.land](http://www.transit.land)), otherwise 
these files have to be obtained from the public transit agency. The Swiss public transit schedule is available GTFS and HAFAS 
format via [opentransportdata.swiss](https://opentransportdata.swiss). Public transit data feeds can be converted to unmapped 
MATSim transit schedules using the classes _Gtfs2Transitschedule_ or _Hafas2TransitSchedule_. It is possible to convert public 
transit information from OpenStreetMap files (_Osm2TransitSchedule_). However, OSM currently does not contain any temporal 
information, the accuracy of the schedule data varies and is usually not sufficient to be used for simulations.

Unmapped transit schedules lack information on the links used by vehicles and only contain the stop sequence
for transit routes. Generating these links (i.e. the path a vehicle takes on a network) is called "mapping", a process
done by the _PublicTransitMapper_. It implements an [algorithm](https://github.com/matsim-org/pt2matsim/wiki/PTMapper-algorithm-and-config-parameters) 
that uses an abstract graph to calculate the least cost path from the transit route's first to its last stop with the constraint 
that the path must contain a so called link candidate for every stop. The result is a mapped transit schedule and a modified network.

Tools to validate and rudimentarily edit the mapped schedule are also part of the package.

### Package Workflow

[The wiki contains information on how to run the converters and mappers.](https://github.com/matsim-org/pt2matsim/wiki)

![pt2matsim workflow](doc/pt2matsim_workflow.jpg)

### Binaries

Releases are available on [repo.matsim.org](https://repo.matsim.org/service/rest/repository/browse/matsim/org/matsim/pt2matsim/). Run the _-shaded.jar_ for standalone usage.

To include pt2matsim in your own maven project, add this snippet to your pom.xml:

    <repositories>
        <repository>
            <id>pt2matsim</id>
            <url>https://repo.matsim.org/repository/matsim/</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>org.matsim</groupId>
            <artifactId>pt2matsim</artifactId>
            <version>22.3</version>
        </dependency>
    </dependencies>

The master branch contains the snapshot version with the latest changes. Clone the git repository to use it.



### Example usage

#### 1-Osm2MultimodalNetwork
/home/geompr/Documents/Leeds/RAIM/code/RAIM/matsim-raim/original-input-data/configFileWestmidlands.xml

/home/geompr/Documents/Leeds/RAIM/code/RAIM/matsim-raim/original-input-data/configFileWinnipeg.xml


#### 2-Gtfs2TransitSchedule
/home/geompr/Documents/Leeds/RAIM/code/RAIM/data/westmidlands/supply/tfwm_gtfs "20221121" EPSG:27700 unmapped_transit_schedule_monday.xml vehicles_monday.xml

/home/geompr/Documents/Leeds/RAIM/code/RAIM/data/winnipeg/supply/winnipeg_gtfs_filtered "20211213" EPSG:3158 unmapped_transit_schedule_monday.xml vehicles_monday.xml

if vehicles file is generated: check networkMode and correct manually if needed


#### 3-PublicTransitMapper
/home/geompr/Documents/Leeds/RAIM/code/RAIM/matsim-raim/original-input-data/configPTMapperWestmidlands.xml

/home/geompr/Documents/Leeds/RAIM/code/RAIM/matsim-raim/original-input-data/configPTMapperWinnipeg.xml


#### 4-CheckMappedSchedulePlausibility
/home/geompr/Documents/Leeds/RAIM/code/RAIM/data/westmidlands/transit_schedule_monday.xml /home/geompr/Documents/Leeds/RAIM/code/RAIM/data/westmidlands/network_multimodal_monday.xml EPSG:27700 /home/geompr/Documents/Leeds/RAIM/code/RAIM/data/westmidlands/plausibilityCheck_monday/

/home/geompr/Documents/Leeds/RAIM/code/RAIM/data/winnipeg/transit_schedule_monday.xml /home/geompr/Documents/Leeds/RAIM/code/RAIM/data/winnipeg/network_multimodal_monday.xml EPSG:3158  /home/geompr/Documents/Leeds/RAIM/code/RAIM/data/winnipeg/plausibilityCheck_monday/

