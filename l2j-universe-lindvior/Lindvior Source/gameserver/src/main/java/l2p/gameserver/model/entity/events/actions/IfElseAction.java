package l2p.gameserver.model.entity.events.actions;

import l2p.gameserver.model.entity.events.EventAction;
import l2p.gameserver.model.entity.events.GlobalEvent;

import java.util.Collections;
import java.util.List;

/**
 * @author VISTALL
 * @date 8:38/05.03.2011
 */
public class IfElseAction implements EventAction {
    private String _name;
    private boolean _reverse;
    private List<EventAction> _ifList = Collections.emptyList();
    private List<EventAction> _elseList = Collections.emptyList();

    public IfElseAction(String name, boolean reverse) {
        _name = name;
        _reverse = reverse;
    }

    @Override
    public void call(GlobalEvent event) {
        List<EventAction> list = (_reverse ? !event.ifVar(_name) : event.ifVar(_name)) ? _ifList : _elseList;
        for (EventAction action : list)
            action.call(event);
    }

    public void setIfList(List<EventAction> ifList) {
        _ifList = ifList;
    }

    public void setElseList(List<EventAction> elseList) {
        _elseList = elseList;
    }
}
