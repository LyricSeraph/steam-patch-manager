package top.lyriclaw.spm.backend.vo.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class DetailedPatchVo extends PatchVo {

    private List<StorageVo> files;

}
