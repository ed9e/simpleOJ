package database;

/**
 * @author ed9e
 * @ClassName: ProblemCell
 * @Description: problem ADT
 * @date 2019/11/17
 * @Copyright
 */
public class ProblemCell {
    private Integer id;
    private String title;
    private String description;
    private Integer level;
    private Integer runtimeLimit;
    private Integer memoryLimit;
    ProblemCell(Integer id,String title,String description,Integer level,Integer runtimeLimit,Integer memoryLimit)
    {
        this.id=id;
        this.title=title;
        this.description=description;
        this.level=level;
        this.runtimeLimit=runtimeLimit;
        this.memoryLimit=memoryLimit;
    }
    ProblemCell(){}
    public void setId(Integer id){this.id=id;}
    public void setTitle(String title){this.title=title;}
    public void setDescription(String description){this.description=description;}
    public void setLevel(Integer level){this.level=level;}
    public void setRuntimeLimit(Integer runtimeLimit){this.runtimeLimit=runtimeLimit;}
    public void setMemoryLimit(Integer memoryLimit){this.memoryLimit=memoryLimit;}

    public Integer getRuntimeLimit(){return runtimeLimit;}
    public Integer getMemoryLimit(){return memoryLimit;}
    public String getTitle(){return title;}
    public String getDescription(){return description;}
    public Integer getId(){return id;}
    @Override
    public String toString() {
        return getTitle();
    }
}
