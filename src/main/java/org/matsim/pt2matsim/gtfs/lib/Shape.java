/* *********************************************************************** *
 * project: org.matsim.*
 * Shape.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2011 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.pt2matsim.gtfs.lib;

import com.vividsolutions.jts.geom.Coordinate;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.core.utils.collections.Tuple;
import org.matsim.core.utils.geometry.geotools.MGC;
import org.matsim.pt.transitSchedule.api.TransitLine;
import org.matsim.pt.transitSchedule.api.TransitRoute;
import org.matsim.pt.transitSchedule.api.TransitRouteStop;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Shape {

	//Attributes
	/**
	 * The id
	 */
	private String id;

	/**
	 * The points of the shape
	 */
	private SortedMap<Integer, Coord> points;

	/**
	 * A shape can be referenced to multiple transit routes
	 */
	private Set<Tuple<Id<TransitLine>, Id<TransitRoute>>> transitRoutes;
	private Coord[] extent = new Coord[]{new Coord(Double.MAX_VALUE, Double.MAX_VALUE), new Coord(Double.MIN_VALUE, Double.MIN_VALUE)};


	//Methods

	/**
	 * Constructs
	 */
	public Shape(String id) {
		this.id = id;
		this.points = new TreeMap<>();
		this.transitRoutes = new HashSet<>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the points
	 */
	public SortedMap<Integer, Coord> getPoints() {
		return points;
	}

	/**
	 * Adds a new point
	 */
	public void addPoint(Coord point, int pos) {
		points.put(pos, point);

		if(point.getX() < extent[0].getX()) {
			extent[0].setX(point.getX());
		}
		if(point.getY() < extent[0].getY()) {
			extent[0].setY(point.getY());
		}
		if(point.getX() > extent[1].getX()) {
			extent[1].setX(point.getX());
		}
		if(point.getY() > extent[1].getY()) {
			extent[1].setY(point.getY());
		}
	}

	public Coordinate[] getCoordinates() {
		if(points.size() == 0) {
			return null;
		} else {
			int i = 0;
			Coordinate[] coordinates = new Coordinate[points.values().size()];
			for(Coord coord : points.values()) {
				coordinates[i++] = MGC.coord2Coordinate(coord);
			}
			return coordinates;
		}
	}

	public void addTransitRoute(Id<TransitLine> transitLineId, Id<TransitRoute> transitRouteId) {
		this.transitRoutes.add(new Tuple<>(transitLineId, transitRouteId));
	}

	public Set<Tuple<Id<TransitLine>, Id<TransitRoute>>> getTransitRoutes() {
		return transitRoutes;
	}

	public Coord[] getExtent() {
		return extent;
	}
}
