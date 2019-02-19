package pl.bartlomiejstepien;

import pl.bartlomiejstepien.events.Event;
import pl.bartlomiejstepien.events.MessageReceiveEvent;
import pl.bartlomiejstepien.listeners.EventListener;
import pl.bartlomiejstepien.listeners.Listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventManager
{
    private static EventManager INSTANCE = new EventManager();
    private final List<Listener> eventListeners = new ArrayList<>();

    public static EventManager getInstance()
    {
        return INSTANCE;
    }

    public void runEvent(Event event)
    {
        Class listenerToTrigger = null;
        if(event instanceof MessageReceiveEvent)
            listenerToTrigger = MessageReceiveEvent.class;

        if(listenerToTrigger == null)
            return;

        for(Listener listener : eventListeners)
        {
            Method[] methods = listener.getClass().getMethods();
            for(Method method : methods)
            {
                if(method.isAnnotationPresent(EventListener.class))
                {
                    if(method.getParameterCount() != 1)
                        return;

                    if(method.getParameters()[0].getType().equals(listenerToTrigger))
                    {
                        try
                        {
                            method.invoke(listener, event);
                        }
                        catch(IllegalAccessException e)
                        {
                            e.printStackTrace();
                        }
                        catch(InvocationTargetException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public List<Listener> getEventListeners()
    {
        return this.eventListeners;
    }

    public void registerEventListener(Listener listener)
    {
        if(!this.eventListeners.contains(listener))
            this.eventListeners.add(listener);
    }
}
