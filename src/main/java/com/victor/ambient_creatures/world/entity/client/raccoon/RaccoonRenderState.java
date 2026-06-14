package com.victor.ambient_creatures.world.entity.client.raccoon;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class RaccoonRenderState extends LivingEntityRenderState
{
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState standingAnimationState = new AnimationState();
}
