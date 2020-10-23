package ru.geekbrains.handmade.ltmbackend.core.configurations.security.acl.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.geekbrains.handmade.ltmbackend.core.configurations.security.acl.PermissionEvaluatorHandler;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Aspect
@Component
public class PermissionEvaluatorAspect {

    private final SpelExpressionParser parser = new SpelExpressionParser();

    private final UserService userService;
    private final Map<String, PermissionEvaluatorHandler> permissionEvaluatorHandlers;


    public PermissionEvaluatorAspect(UserService userService,
                                     List<PermissionEvaluatorHandler> permissionEvaluatorHandlers) {

        this.userService = userService;
        this.permissionEvaluatorHandlers = permissionEvaluatorHandlers.stream()
            .collect(Collectors.toMap(PermissionEvaluatorHandler::getKey, eh -> eh));

    }

    /**
     * Check User privileges to access entities
     */
    @Before("@annotation(CheckPrivileges)")
    public void beforeCheckPrivileges(JoinPoint joinPoint) {

        //Get the method's parameter name and value
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        List<String> paramNameList = Arrays.asList(methodSignature.getParameterNames());
        List<Object> paramList = Arrays.asList(joinPoint.getArgs());

        //Place method parameter names and values in context
        EvaluationContext ctx = new StandardEvaluationContext();
        for (int i = 0; i < paramNameList.size(); i++) {
            ctx.setVariable(paramNameList.get(i), paramList.get(i));
        }

        CheckPrivileges checkPrivileges = AnnotationUtils.findAnnotation(method, CheckPrivileges.class);

        Assert.notNull(checkPrivileges, "@CheckPrivileges == null");
        Long targetId = (Long)parser.parseExpression(checkPrivileges.targetId()).getValue(ctx);
        Class<?> targetClass = checkPrivileges.targetClass();
        Set<String> permissions = new HashSet<>(Arrays.asList(checkPrivileges.permission()));
        String key = targetClass.getName();

        if(permissionEvaluatorHandlers.containsKey(key)) {
            if(!permissionEvaluatorHandlers.get(key).hasPermission(
                userService.getCurrent(),
                targetId,
                permissions)) {
                throw new AccessDeniedException("Unauthorized");
            }
        }

//        // Parse SpEL expression to get results
//        String value = parser.parseExpression(sensitiveOverride.key()).getValue(ctx).toString();
//        //Gets the InvocationHandler held by this proxy instance sensitiveOverride
//        InvocationHandler invocationHandler = Proxy.getInvocationHandler(sensitiveOverride);
//        // Get memberValues field of invocationHandler
//        Field hField = invocationHandler.getClass().getDeclaredField("memberValues");
//        // Since this field is a private final modifier, open permissions
//        hField.setAccessible(true);
//        // Get memberValues
//        Map memberValues = (Map) hField.get(invocationHandler);
//        // Modify the value property value
//        memberValues.put("userType", Integer.parseInt(value));

    }
}
