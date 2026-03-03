package com.sprint.mission.discodeit.repository.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.sprint.mission.discodeit.entity.BaseEntity;
import com.sprint.mission.discodeit.entity.Message;

public abstract class FileRepository<T extends BaseEntity> {
    
    protected final String filePath; // 데이터를 저장할 실제 파일 경로
    protected Map<UUID, T> dataMap = new ConcurrentHashMap<>(); // 메모리 상에서 데이터를 관리하는 저장소

    protected FileRepository(String filePath) {
        this.filePath = filePath;
        load(); // 생성 시 파일에서 읽어오기
    }

    private void load() {
        File file = new File(filePath);
        if (!file.exists()) {
            return; // 파일이 없으면 그냥 빈 상태로 시작 - 빈 Map으로 시작
        }
        
        try (
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis)
        ) {
            Object object = ois.readObject();
            if (object instanceof Map) {
                this.dataMap = new ConcurrentHashMap<>((Map<UUID, T>)object);
            }

        } catch (EOFException e) {
            System.out.println("파일이 비어있습니다.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("파일 로딩 중 오류 발생: " + e.getMessage());
        }
    }

    protected synchronized void saveToFile() {
        try (
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos)
        ) {
            // ConcurrentHashMap은 직렬화가 까다로울 수 있으므로 
            // 저장할 때 일반 HashMap으로 복사해서 저장
            oos.writeObject(new HashMap<>(dataMap));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("파일 저장 중 오류 발생: " + e.getMessage());
        }
    }

    public synchronized T save(T entity) {
        dataMap.put(entity.getId(), entity);
        saveToFile();
        return entity;
    }

    public synchronized void deleteById(UUID id) {
        dataMap.remove(id);
        saveToFile();
    }

    public Optional<T> findById(UUID id) {
        return Optional.ofNullable(dataMap.get(id)); // map.get(id) => value
    }

    public List<T> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(dataMap.values()));
    }

}
