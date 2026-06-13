package com.victor.ambient_creatures.world.entity.client.capybara.adult;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.geom.PartNames;

public class AdultCapybaraAnimations
{

    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(10f)
            .looping()
            .addAnimation(PartNames.HEAD, new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.LEFT_EAR, new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.875f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.95833f, KeyframeAnimations.degreeVec(-72.26f, -19.88f, 29.92f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.04167f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.125f, KeyframeAnimations.degreeVec(-72.26f, -19.88f, 29.92f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(3.20833f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.58333f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.66667f, KeyframeAnimations.degreeVec(-72.26f, -19.88f, 29.92f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.RIGHT_EAR, new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.08333f, KeyframeAnimations.degreeVec(-75.24f, 19.89f, -34.29f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.16667f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(5.08333f, KeyframeAnimations.degreeVec(-75.24f, 19.89f, -34.29f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(5.16667f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.45833f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.54167f, KeyframeAnimations.degreeVec(-75.24f, 19.89f, -34.29f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(8.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(10f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();
    public static final AnimationDefinition WALKING = AnimationDefinition.Builder.withLength(1f)
            .looping()
            .addAnimation(PartNames.ROOT, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.HEAD, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.LEFT_FRONT_LEG, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.LEFT_FRONT_LEG, new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.RIGHT_FRONT_LEG, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.RIGHT_FRONT_LEG, new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.LEFT_HIND_LEG, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.LEFT_HIND_LEG, new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.RIGHT_HIND_LEG, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.RIGHT_HIND_LEG, new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25f, KeyframeAnimations.degreeVec(25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();
    public static final AnimationDefinition SITTING = AnimationDefinition.Builder.withLength(10f)
            .looping()
            .addAnimation(PartNames.ROOT, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(10f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.BODY, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -3f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9.95833f, KeyframeAnimations.posVec(0f, -3f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.BODY, new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.HEAD, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(10f, KeyframeAnimations.posVec(0f, -1f, 1f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.HEAD, new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.LEFT_EAR, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9.95833f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.RIGHT_EAR, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9.95833f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.LEFT_FRONT_LEG, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.6f, -3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9.95833f, KeyframeAnimations.posVec(0f, 0.6f, -3f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.LEFT_FRONT_LEG, new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.RIGHT_FRONT_LEG, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0f, 0.6f, -3f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9.95833f, KeyframeAnimations.posVec(0f, 0.6f, -3f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.RIGHT_FRONT_LEG, new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.LEFT_HIND_LEG, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(0.1f, 2f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9.95833f, KeyframeAnimations.posVec(0.1f, 2f, 1f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.LEFT_HIND_LEG, new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(60f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.RIGHT_HIND_LEG, new AnimationChannel(
                    AnimationChannel.Targets.POSITION,
                    new Keyframe(0f, KeyframeAnimations.posVec(-0.1f, 2f, 1f), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(9.95833f, KeyframeAnimations.posVec(-0.1f, 2f, 1f), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation(PartNames.RIGHT_HIND_LEG, new AnimationChannel(
                    AnimationChannel.Targets.ROTATION,
                    new Keyframe(0f, KeyframeAnimations.degreeVec(60f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();
}
