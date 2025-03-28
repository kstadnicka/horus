package org.ks.horus;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FileCabinet implements Cabinet{

    private final List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders= Optional.ofNullable(folders)
                .orElseGet(ArrayList::new)
                .stream()
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        Objects.requireNonNull(name);
        List<Folder> foldersByName = folders.stream()
                .filter(folder -> name.equals(folder.getName()))
                .toList();
        if (foldersByName.size() > 1){
            throw new IllegalStateException("More than one folder with the given name was found");
        }
        return foldersByName.stream()
                .findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        Objects.requireNonNull(size);
        Size folderSize;
        try {
            folderSize = Size.valueOf(size);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Provided size is not SMALL, MEDIUM or LARGE");
        }
        return folders.stream()
                .filter(folder -> folderSize.equals(folder.getSize()))
                .toList();
    }

    @Override
    public int count() {
        return folders.size();
    }
}
