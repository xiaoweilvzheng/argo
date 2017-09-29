package com.jx.argo.internal;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.jx.argo.ActionResult;
import com.jx.argo.Argo;
import com.jx.argo.ArgoController;
import com.jx.argo.BeatContext;
import com.jx.argo.inject.ArgoSystem;
import com.jx.argo.logs.Logger;
import com.jx.argo.route.*;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.List;
import java.util.Set;

@Singleton
public class DefaultRouter implements Router {

    private final Argo argo;
    private final List<Action> actions;

    @Inject
    public DefaultRouter(Argo argo, @ArgoSystem Set<Class<? extends ArgoController>> controllerClasses, @StaticActionAnnotation Action staticAction) {

        this.argo = argo;

        argo.getLogger().info("initializing a %s(implements Router)", this.getClass());

        this.actions = buildActions(argo, controllerClasses, staticAction);

        argo.getLogger().info("%s(implements Router) constructed.", this.getClass());
    }

    @Override
    public ActionResult route(BeatContext beat) {

        RouteBag bag = RouteBag.create(beat);

        for(Action action : actions) {
            RouteResult routeResult = action.matchAndInvoke(bag);
            if (routeResult.isSuccess())
                return routeResult.getResult();
        }

        return ActionResult.NULL;
    }

    List<Action> buildActions(Argo argo, Set<Class<? extends ArgoController>> controllerClasses, Action staticAction) {

        Set<ArgoController> controllers = getControllerInstances(argo, controllerClasses);
        return buildActions(controllers, staticAction);
    }

    private Set<ArgoController> getControllerInstances(final Argo argo, Set<Class<? extends ArgoController>> controllerClasses) {

        Iterable<ArgoController> sets = Iterables.transform(controllerClasses, new Function<Class<? extends ArgoController>, ArgoController>() {
            @Override
            public ArgoController apply(Class<? extends ArgoController> clazz) {

                // instance a controller
                ArgoController controller = argo.getInstance(clazz);
                // initialize the controller.
                controller.init();

                return controller;
            }
        });

        return ImmutableSet.copyOf(sets);
    }


    //TODO:static files actions.
    List<Action> buildActions(Set<ArgoController> controllers, Action staticAction) {
        
    	List<Action> actions = Lists.newArrayList();
        actions.add(staticAction);

        for (ArgoController controller : controllers) {
            ControllerInfo controllerInfo = new ControllerInfo(controller);
            List<ActionInfo> subActions = controllerInfo.analyze();

            for(ActionInfo newAction : subActions)
                merge(actions, MethodAction.create(newAction));

        }

        return ImmutableList.copyOf(actions);
    }

    void merge(List<Action> actions, Action newAction) {

        for (int index = 0; index < actions.size(); index++) {
            Action action = actions.get(index);
            if(action.order() > newAction.order()) {
                actions.add(index, newAction);
                return;
            }
        }

        actions.add(newAction);
    }

}