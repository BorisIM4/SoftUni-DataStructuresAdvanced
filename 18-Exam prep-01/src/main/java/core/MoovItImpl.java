package core;

import com.sun.source.tree.IfTree;
import models.Route;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MoovItImpl implements MoovIt {
    private final Map<String, Route> routesById;

    public MoovItImpl() {
        this.routesById = new LinkedHashMap<>();
    }

    @Override
    public void addRoute(Route route) {
        if (this.routesById.containsKey(route.getId())) {
            throw new IllegalArgumentException();
        }

        String id = route.getId();
        this.routesById.put(id, route);
    }

    @Override
    public void removeRoute(String routeId) {
        Route route = this.routesById.get(routeId);

        if (route == null) {
            throw new IllegalArgumentException();
        }

        this.routesById.remove(routeId);
    }

    @Override
    public boolean contains(Route route) {
        return this.routesById.containsValue(route);
    }

    @Override
    public int size() {
        return this.routesById.size();
    }

    @Override
    public Route getRoute(String routeId) {
        Route route = this.routesById.get(routeId);

        if (route == null) {
            throw new IllegalArgumentException();
        }

        return route;
    }

    @Override
    public void chooseRoute(String routeId) {
        if (!this.routesById.containsKey(routeId)) {
            throw new IllegalArgumentException();
        }

        Integer popularity = this.routesById.get(routeId).getPopularity();
        Route currentRoute = this.routesById.get(routeId);
        currentRoute.setPopularity(popularity + 1);
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        return this.routesById.values()
                .stream()
                .filter(r -> {
                    List<String> points = r.getLocationPoints();

                    int startIndex = points.indexOf(startPoint);
                    int endIndex = points.indexOf(endPoint);

                    return startIndex > 0 && endIndex >= 0 && startIndex < endIndex;
                })
                .sorted((l, r) -> {
                    if (l.getIsFavorite() && !r.getIsFavorite()) {
                        return -1;
                    }

                    if (r.getIsFavorite() && !l.getIsFavorite()) {
                        return 1;
                    }

                    int lDistance = l.getLocationPoints().indexOf(endPoint) - l.getLocationPoints().indexOf(startPoint);
                    int rDistance = r.getLocationPoints().indexOf(endPoint) - r.getLocationPoints().indexOf(startPoint);

                    if (lDistance != rDistance) {
                        return lDistance - rDistance;
                    }

                    return r.getPopularity() - l.getPopularity();
                })
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        return this.routesById.values()
                .stream()
                .filter(route -> {
                    int pointIndex = route.getLocationPoints().indexOf(destinationPoint);

                    return route.getIsFavorite() && pointIndex > 0;
                })
                .sorted((l, r) -> {
                    if (l.getDistance() != r.getDistance()) {
                        return Double.compare(l.getDistance(), r.getDistance());
                    }

                    return r.getPopularity() - l.getPopularity();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Route> getTop5RoutesByPopularityThenByDistanceThenByCountOfLocationPoints() {
        return this.routesById.values()
                .stream()
                .sorted((l, r) -> {
                    if (!l.getPopularity().equals(r.getPopularity())) {
                        return r.getPopularity() - l.getPopularity();
                    }

                    if (!l.getDistance().equals(r.getDistance())) {
                        return Double.compare(l.getDistance(), r.getDistance());
                    }

                    return l.getLocationPoints().size() - r.getLocationPoints().size();
                })
                .limit(5)
                .collect(Collectors.toList());
    }
}



























