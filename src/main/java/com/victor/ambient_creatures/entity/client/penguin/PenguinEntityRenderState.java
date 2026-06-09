package com.victor.ambient_creatures.entity.client.penguin;

import net.minecraft.client.renderer.entity.state.HoldingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class PenguinEntityRenderState extends HoldingEntityRenderState
{
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkingAnimationState = new AnimationState();
    public final AnimationState swimIdleAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState slidingAnimationState = new AnimationState();

    public boolean touchingWater;
}
