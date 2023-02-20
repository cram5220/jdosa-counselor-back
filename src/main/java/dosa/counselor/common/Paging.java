package dosa.counselor.common;


import lombok.Builder;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Paging {
    Pageable pageable;
    Integer pageCount;
    Integer last;
    Boolean hasPrevious;
    Boolean hasNext;
    List<Map<String,Integer>> pageList;

    @Builder
    public Paging(Pageable pageable, Long keywordCount){
        this.pageable = pageable;
        this.pageCount = (int)Math.ceil(keywordCount*1.0/pageable.getPageSize());
        this.last = this.pageCount - 1;
        this.pageList = new ArrayList<>();
        int i = pageable.getPageNumber()-4;
        if(i<0){ i=0; }
        while(i<=pageable.getPageNumber()+4 && i>=0 && i<this.pageCount){
            Map<String,Integer> pageMap = new HashMap<>();
            pageMap.put("label",i+1);
            pageMap.put("pageNum",i);
            this.pageList.add(pageMap);
            i++;
        }
        this.hasPrevious = pageable.hasPrevious();
        if(pageable.getPageNumber()<this.pageCount-1){
            this.hasNext = true;
        }else{
            this.hasNext = false;
        }

    }


    @Builder
    public Paging(Pageable pageable, Integer keywordCount){
        this.pageable = pageable;
        this.pageCount = (int)Math.ceil(keywordCount*1.0/pageable.getPageSize());
        this.last = this.pageCount - 1;
        this.pageList = new ArrayList<>();
        int i = pageable.getPageNumber()-4;
        if(i<0){ i=0; }
        while(i<=pageable.getPageNumber()+4 && i>=0 && i<this.pageCount){
            Map<String,Integer> pageMap = new HashMap<>();
            pageMap.put("label",i+1);
            pageMap.put("pageNum",i);
            this.pageList.add(pageMap);
            i++;
        }
        this.hasPrevious = pageable.hasPrevious();
        if(pageable.getPageNumber()<this.pageCount-1){
            this.hasNext = true;
        }else{
            this.hasNext = false;
        }

    }
}
