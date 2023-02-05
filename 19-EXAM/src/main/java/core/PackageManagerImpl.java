package core;

import models.Package;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class PackageManagerImpl implements PackageManager {
    private final Map<String, Package> packageById;

    private final Set<Package> packages;

    private final Map<Package, List<Package>> dependencies;

    public PackageManagerImpl() {
        this.packageById = new LinkedHashMap<>();

        this.packages = new LinkedHashSet<>();

        this.dependencies = new LinkedHashMap<>();
    }

    @Override
    public void registerPackage(Package _package) {
        if (this.packages.contains(_package)) {
            throw new IllegalArgumentException();
        }

        this.packageById.put(_package.getId(), _package);
        this.packages.add(_package);
    }

    @Override
    public void removePackage(String packageId) {
        Package result = this.packageById.remove(packageId);

        if (result == null) {
            throw new IllegalArgumentException();
        }

        this.packages.remove(result);
    }

    @Override
    public void addDependency(String packageId, String dependencyId) {
        Package mainPack = this.packageById.get(packageId);
        Package depPack = this.packageById.get(dependencyId);
        if (mainPack == null || depPack == null) {
            throw new IllegalArgumentException();
        }

        if (!this.dependencies.containsKey(mainPack)) {
            this.dependencies.put(mainPack, new ArrayList<>());
        }

        List<Package> packages = this.dependencies.get(mainPack);
        packages.add(depPack);
        this.dependencies.put(mainPack, packages);

    }

    @Override
    public boolean contains(Package _package) {
        return this.packages.contains(_package);
    }

    @Override
    public int size() {
        return this.packageById.size();
    }

    @Override
    public Iterable<Package> getDependants(Package _package) {
        return this.packages.stream()
                .filter(this.dependencies::containsKey)
                .collect(Collectors.toList());
//        List<Package> result = this.dependencies.get(_package);
//
//        if (result == null) {
//            return new ArrayList<>();
//        }
//
//        return result;
    }

    @Override
    public Iterable<Package> getIndependentPackages() {
        return this.packages.stream()
                .filter(p -> !this.dependencies.containsKey(p))
                .sorted((l, r) -> {
                    LocalDateTime leftReleaseDate = l.getReleaseDate();
                    LocalDateTime rightReleaseDate = r.getReleaseDate();


                    if (leftReleaseDate != rightReleaseDate) {
                        return rightReleaseDate.compareTo(leftReleaseDate);
                   }

                   return l.getVersion().compareTo(r.getVersion());
               })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Package> getOrderedPackagesByReleaseDateThenByVersion() {
        return this.packageById.values()
                .stream()
                .sorted((l, r) -> {
                    LocalDateTime leftDate = l.getReleaseDate();
                    LocalDateTime rightDate = r.getReleaseDate();

                    if (leftDate != rightDate) {
                        return rightDate.compareTo(leftDate);
                    } else {
                        if (l.getName().equals(r.getName())) {
                            return r.getVersion().compareTo(l.getVersion());
                        }
                    }

                    return l.getVersion().compareTo(r.getVersion());
                })
                .collect(Collectors.toList());
    }
}
