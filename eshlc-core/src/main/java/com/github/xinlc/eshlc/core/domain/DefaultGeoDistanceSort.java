package com.github.xinlc.eshlc.core.domain;

import com.github.xinlc.eshlc.core.enums.SortDirectionType;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoValidationMethod;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.elasticsearch.search.sort.GeoDistanceSortBuilder.DEFAULT_VALIDATION;

/**
 * ES 地理位置排序默认实现
 *
 * @author Richard
 * @since 2021-04-05
 */
class DefaultGeoDistanceSort extends AbstractSort implements IEsGeoDistanceSort {

    private final List<GeoPoint> points = new ArrayList<>();
    private GeoDistance geoDistance = GeoDistance.ARC;
    private DistanceUnit unit = DistanceUnit.DEFAULT;
    private GeoValidationMethod validation = DEFAULT_VALIDATION;
    private boolean ignoreUnmapped = false;

    DefaultGeoDistanceSort(String name, SortDirectionType direction) {
        super(name, direction);
    }

    @Override
    public GeoDistance geoDistance() {
        return geoDistance;
    }

    @Override
    public IEsGeoDistanceSort geoDistance(GeoDistance distance) {
        this.geoDistance = distance;
        return this;
    }

    @Override
    public DistanceUnit unit() {
        return unit;
    }

    @Override
    public IEsGeoDistanceSort unit(DistanceUnit unit) {
        this.unit = unit;
        return this;
    }

    @Override
    public GeoValidationMethod validation() {
        return validation;
    }

    @Override
    public IEsGeoDistanceSort validation(GeoValidationMethod validation) {
        this.validation = validation;
        return this;
    }

    @Override
    public boolean ignoreUnmapped() {
        return ignoreUnmapped;
    }

    @Override
    public IEsGeoDistanceSort ignoreUnmapped(boolean ignoreUnmapped) {
        this.ignoreUnmapped = ignoreUnmapped;
        return this;
    }

    @Override
    public List<GeoPoint> points() {
        return Collections.unmodifiableList(points);
    }

    @Override
    public IEsGeoDistanceSort addPoint(GeoPoint point) {
        this.points.add(point);
        return this;
    }

    @Override
    public IEsGeoDistanceSort addPoint(double lat, double lon) {
        return addPoint(new GeoPoint(lat, lon));
    }

    @Override
    public IEsGeoDistanceSort addPoint(String value) {
        return addPoint(new GeoPoint(value));
    }

    @Override
    public SortBuilder<?> toSortBuilder() {
        GeoDistanceSortBuilder builder = SortBuilders.geoDistanceSort(name(), points().toArray(new GeoPoint[0]))
            .ignoreUnmapped(ignoreUnmapped)
            .geoDistance(geoDistance)
            .unit(unit)
            .order(direction().getOrder());

        setSortMode(builder::sortMode);
        return builder;
    }
}
