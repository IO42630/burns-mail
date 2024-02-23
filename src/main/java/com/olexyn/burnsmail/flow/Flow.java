package com.olexyn.burnsmail.flow;

import com.olexyn.burnsmail.flow.action.Action;
import com.olexyn.burnsmail.flow.cleanup.Cleanup;
import com.olexyn.burnsmail.flow.filter.FilterAMail;
import com.olexyn.burnsmail.flow.map.ToAMailMapper;
import com.olexyn.burnsmail.flow.search.SearchFolder;
import com.olexyn.burnsmail.flow.transform.TransformAMail;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Flow {

    private SearchFolder searchFolder;
    private ToAMailMapper toAMailMapper;
    private FilterAMail filterAMail;
    private TransformAMail transformAMail;
    private Action action;
    private Cleanup cleanup;

}
