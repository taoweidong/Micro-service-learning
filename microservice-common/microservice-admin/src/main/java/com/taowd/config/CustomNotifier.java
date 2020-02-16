package com.taowd.config;

import com.alibaba.fastjson.JSON;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import reactor.core.publisher.Mono;

/**
 * 自定义事件通知：在这里可以进行自定义的服务上线下线通知<br/>
 * @author Taoweidong
 */
public class CustomNotifier extends AbstractStatusChangeNotifier {

  public CustomNotifier(InstanceRepository repository) {
    super(repository);
  }

  @Override
  protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
    System.out.println("InstanceEvent--->" + JSON.toJSONString(event));
    System.out.println("Instance--->" + JSON.toJSONString(instance));
    System.out.println("InstanceNotifier--->" +
        JSON.toJSONString(JSON.parseObject(JSON.toJSONString(instance), InstanceNotifier.class)));

    System.out.println(instance.getRegistration().getName().toLowerCase());

    if (event instanceof InstanceStatusChangedEvent) {
      InstanceStatusChangedEvent instanceStatusChangedEvent = (InstanceStatusChangedEvent) event;
      System.out.println("Instance--->" + JSON.toJSONString(instanceStatusChangedEvent));
      System.out.println("服务状态：--->" + instanceStatusChangedEvent.getStatusInfo().getStatus());
    }

    return Mono.fromRunnable(() -> {
      //  TODO    自定义通知的具体实现
      if (event instanceof InstanceStatusChangedEvent) {
        String.format("Instance {} ({}) is {}", instance.getRegistration().getName(),
            event.getInstance(),
            ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());
      } else {
        String.format("Instance {} ({}) {}", instance.getRegistration().getName(),
            event.getInstance(),
            event.getType());
      }
    });
  }
}
