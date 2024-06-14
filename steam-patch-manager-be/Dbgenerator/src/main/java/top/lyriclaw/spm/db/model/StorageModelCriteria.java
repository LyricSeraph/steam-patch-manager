package top.lyriclaw.spm.db.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StorageModelCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StorageModelCriteria() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> idCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
            idCriteria = new ArrayList<>();
        }

        public List<Criterion> getIdCriteria() {
            return idCriteria;
        }

        protected void addIdCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            idCriteria.add(new Criterion(condition, value, "top.lyriclaw.spm.db.typehandler.UuidTypeHandler"));
            allCriteria = null;
        }

        protected void addIdCriterion(String condition, UUID value1, UUID value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            idCriteria.add(new Criterion(condition, value1, value2, "top.lyriclaw.spm.db.typehandler.UuidTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || idCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(idCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
            allCriteria = null;
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(UUID value) {
            addIdCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(UUID value) {
            addIdCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(UUID value) {
            addIdCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(UUID value) {
            addIdCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(UUID value) {
            addIdCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(UUID value) {
            addIdCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<UUID> values) {
            addIdCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<UUID> values) {
            addIdCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(UUID value1, UUID value2) {
            addIdCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(UUID value1, UUID value2) {
            addIdCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andStorageTypeIsNull() {
            addCriterion("storage_type is null");
            return (Criteria) this;
        }

        public Criteria andStorageTypeIsNotNull() {
            addCriterion("storage_type is not null");
            return (Criteria) this;
        }

        public Criteria andStorageTypeEqualTo(String value) {
            addCriterion("storage_type =", value, "storageType");
            return (Criteria) this;
        }

        public Criteria andStorageTypeNotEqualTo(String value) {
            addCriterion("storage_type <>", value, "storageType");
            return (Criteria) this;
        }

        public Criteria andStorageTypeGreaterThan(String value) {
            addCriterion("storage_type >", value, "storageType");
            return (Criteria) this;
        }

        public Criteria andStorageTypeGreaterThanOrEqualTo(String value) {
            addCriterion("storage_type >=", value, "storageType");
            return (Criteria) this;
        }

        public Criteria andStorageTypeLessThan(String value) {
            addCriterion("storage_type <", value, "storageType");
            return (Criteria) this;
        }

        public Criteria andStorageTypeLessThanOrEqualTo(String value) {
            addCriterion("storage_type <=", value, "storageType");
            return (Criteria) this;
        }

        public Criteria andStorageTypeLike(String value) {
            addCriterion("storage_type like", value, "storageType");
            return (Criteria) this;
        }

        public Criteria andStorageTypeNotLike(String value) {
            addCriterion("storage_type not like", value, "storageType");
            return (Criteria) this;
        }

        public Criteria andStorageTypeIn(List<String> values) {
            addCriterion("storage_type in", values, "storageType");
            return (Criteria) this;
        }

        public Criteria andStorageTypeNotIn(List<String> values) {
            addCriterion("storage_type not in", values, "storageType");
            return (Criteria) this;
        }

        public Criteria andStorageTypeBetween(String value1, String value2) {
            addCriterion("storage_type between", value1, value2, "storageType");
            return (Criteria) this;
        }

        public Criteria andStorageTypeNotBetween(String value1, String value2) {
            addCriterion("storage_type not between", value1, value2, "storageType");
            return (Criteria) this;
        }

        public Criteria andPatchIdIsNull() {
            addCriterion("patch_id is null");
            return (Criteria) this;
        }

        public Criteria andPatchIdIsNotNull() {
            addCriterion("patch_id is not null");
            return (Criteria) this;
        }

        public Criteria andPatchIdEqualTo(Long value) {
            addCriterion("patch_id =", value, "patchId");
            return (Criteria) this;
        }

        public Criteria andPatchIdNotEqualTo(Long value) {
            addCriterion("patch_id <>", value, "patchId");
            return (Criteria) this;
        }

        public Criteria andPatchIdGreaterThan(Long value) {
            addCriterion("patch_id >", value, "patchId");
            return (Criteria) this;
        }

        public Criteria andPatchIdGreaterThanOrEqualTo(Long value) {
            addCriterion("patch_id >=", value, "patchId");
            return (Criteria) this;
        }

        public Criteria andPatchIdLessThan(Long value) {
            addCriterion("patch_id <", value, "patchId");
            return (Criteria) this;
        }

        public Criteria andPatchIdLessThanOrEqualTo(Long value) {
            addCriterion("patch_id <=", value, "patchId");
            return (Criteria) this;
        }

        public Criteria andPatchIdIn(List<Long> values) {
            addCriterion("patch_id in", values, "patchId");
            return (Criteria) this;
        }

        public Criteria andPatchIdNotIn(List<Long> values) {
            addCriterion("patch_id not in", values, "patchId");
            return (Criteria) this;
        }

        public Criteria andPatchIdBetween(Long value1, Long value2) {
            addCriterion("patch_id between", value1, value2, "patchId");
            return (Criteria) this;
        }

        public Criteria andPatchIdNotBetween(Long value1, Long value2) {
            addCriterion("patch_id not between", value1, value2, "patchId");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNull() {
            addCriterion("filename is null");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNotNull() {
            addCriterion("filename is not null");
            return (Criteria) this;
        }

        public Criteria andFilenameEqualTo(String value) {
            addCriterion("filename =", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotEqualTo(String value) {
            addCriterion("filename <>", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThan(String value) {
            addCriterion("filename >", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThanOrEqualTo(String value) {
            addCriterion("filename >=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThan(String value) {
            addCriterion("filename <", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThanOrEqualTo(String value) {
            addCriterion("filename <=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLike(String value) {
            addCriterion("filename like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotLike(String value) {
            addCriterion("filename not like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameIn(List<String> values) {
            addCriterion("filename in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotIn(List<String> values) {
            addCriterion("filename not in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameBetween(String value1, String value2) {
            addCriterion("filename between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotBetween(String value1, String value2) {
            addCriterion("filename not between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andSizeIsNull() {
            addCriterion("\"size\" is null");
            return (Criteria) this;
        }

        public Criteria andSizeIsNotNull() {
            addCriterion("\"size\" is not null");
            return (Criteria) this;
        }

        public Criteria andSizeEqualTo(Long value) {
            addCriterion("\"size\" =", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotEqualTo(Long value) {
            addCriterion("\"size\" <>", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThan(Long value) {
            addCriterion("\"size\" >", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThanOrEqualTo(Long value) {
            addCriterion("\"size\" >=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThan(Long value) {
            addCriterion("\"size\" <", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThanOrEqualTo(Long value) {
            addCriterion("\"size\" <=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeIn(List<Long> values) {
            addCriterion("\"size\" in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotIn(List<Long> values) {
            addCriterion("\"size\" not in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeBetween(Long value1, Long value2) {
            addCriterion("\"size\" between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotBetween(Long value1, Long value2) {
            addCriterion("\"size\" not between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andMd5IsNull() {
            addCriterion("md5 is null");
            return (Criteria) this;
        }

        public Criteria andMd5IsNotNull() {
            addCriterion("md5 is not null");
            return (Criteria) this;
        }

        public Criteria andMd5EqualTo(String value) {
            addCriterion("md5 =", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotEqualTo(String value) {
            addCriterion("md5 <>", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5GreaterThan(String value) {
            addCriterion("md5 >", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5GreaterThanOrEqualTo(String value) {
            addCriterion("md5 >=", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5LessThan(String value) {
            addCriterion("md5 <", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5LessThanOrEqualTo(String value) {
            addCriterion("md5 <=", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5Like(String value) {
            addCriterion("md5 like", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotLike(String value) {
            addCriterion("md5 not like", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5In(List<String> values) {
            addCriterion("md5 in", values, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotIn(List<String> values) {
            addCriterion("md5 not in", values, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5Between(String value1, String value2) {
            addCriterion("md5 between", value1, value2, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotBetween(String value1, String value2) {
            addCriterion("md5 not between", value1, value2, "md5");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNull() {
            addCriterion("created_at is null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNotNull() {
            addCriterion("created_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtEqualTo(Instant value) {
            addCriterion("created_at =", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotEqualTo(Instant value) {
            addCriterion("created_at <>", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThan(Instant value) {
            addCriterion("created_at >", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThanOrEqualTo(Instant value) {
            addCriterion("created_at >=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThan(Instant value) {
            addCriterion("created_at <", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThanOrEqualTo(Instant value) {
            addCriterion("created_at <=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIn(List<Instant> values) {
            addCriterion("created_at in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotIn(List<Instant> values) {
            addCriterion("created_at not in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtBetween(Instant value1, Instant value2) {
            addCriterion("created_at between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotBetween(Instant value1, Instant value2) {
            addCriterion("created_at not between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNull() {
            addCriterion("updated_at is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNotNull() {
            addCriterion("updated_at is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtEqualTo(Instant value) {
            addCriterion("updated_at =", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotEqualTo(Instant value) {
            addCriterion("updated_at <>", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThan(Instant value) {
            addCriterion("updated_at >", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThanOrEqualTo(Instant value) {
            addCriterion("updated_at >=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThan(Instant value) {
            addCriterion("updated_at <", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThanOrEqualTo(Instant value) {
            addCriterion("updated_at <=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIn(List<Instant> values) {
            addCriterion("updated_at in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotIn(List<Instant> values) {
            addCriterion("updated_at not in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtBetween(Instant value1, Instant value2) {
            addCriterion("updated_at between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotBetween(Instant value1, Instant value2) {
            addCriterion("updated_at not between", value1, value2, "updatedAt");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}